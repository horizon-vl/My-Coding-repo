"""F1 phone steering wheel - main server.

Serves the wheel page (index.html) over HTTPS and receives the phone's inputs
over a WebSocket at /ws. Inputs are turned into a virtual Xbox 360 controller
(ViGEmBus + vgamepad), which F1 25 sees as a normal gamepad.

Run:  python main.py
"""
import asyncio
import ctypes
import datetime
import ipaddress
import json
import socket
import ssl
import sys
import time
from pathlib import Path

from aiohttp import WSMsgType, web
from cryptography import x509
from cryptography.hazmat.primitives import hashes, serialization
from cryptography.hazmat.primitives.asymmetric import rsa
from cryptography.x509.oid import NameOID

try:
    import vgamepad as vg
except Exception as exc:  # not installed, or not on Windows
    vg = None
    print(f"[warn] vgamepad could not be imported ({exc})")

# ---------------------------------------------------------------- config
BASE = Path(__file__).parent
LAYOUT_FILE = BASE / "index.html"
CERT_FILE = BASE / "cert.pem"
KEY_FILE = BASE / "key.pem"
PORT = 8443

TAP_SECONDS = 0.05        # how long a knob "click" holds its virtual button
WATCHDOG_SECONDS = 0.5    # no input from the phone for this long -> reset controls

# Phone button name -> Xbox button (names from vgamepad's XUSB_GAMEPAD_*).
# Bind the matching functions in F1 25's controller settings.
BUTTON_MAP = {
    "down": "LEFT_SHOULDER",    # downshift paddle
    "up": "RIGHT_SHOULDER",     # upshift paddle
    "drs": "A",
    "overtake": "B",            # ERS overtake
    "pit": "X",                 # pit limiter
    "clutch": "Y",              # clutch (held while pressed)
}

# Rotary knob -> (button for "one step up", button for "one step down").
# Turning a knob taps these buttons once per step moved.
#
# An Xbox pad only has 14 usable buttons, which are all taken. Anything written
# as "key:<name>" is sent as a keyboard key instead (see SCANCODES). Bind those
# keys on the KEYBOARD page of F1 25's controls menu. The game window must be
# in front, which it is while you drive.
ROTARY_MAP = {
    "bias": ("DPAD_RIGHT", "DPAD_LEFT"),        # brake bias
    "diff": ("DPAD_UP", "DPAD_DOWN"),           # differential
    "eng": ("RIGHT_THUMB", "LEFT_THUMB"),       # engine mode
    "ersm": ("key:numpad3", "key:numpad2"),     # ERS mode (keyboard keys, see below)
    "fuel": ("START", "BACK"),                  # fuel mix
}


# ---------------------------------------------------------------- keyboard
SCANCODES = {"numpad1": 0x4F, "numpad2": 0x50, "numpad3": 0x51}

if sys.platform == "win32":
    ULONG_PTR = ctypes.c_size_t

    class MOUSEINPUT(ctypes.Structure):
        _fields_ = [("dx", ctypes.c_long), ("dy", ctypes.c_long),
                    ("mouseData", ctypes.c_ulong), ("dwFlags", ctypes.c_ulong),
                    ("time", ctypes.c_ulong), ("dwExtraInfo", ULONG_PTR)]

    class KEYBDINPUT(ctypes.Structure):
        _fields_ = [("wVk", ctypes.c_ushort), ("wScan", ctypes.c_ushort),
                    ("dwFlags", ctypes.c_ulong), ("time", ctypes.c_ulong),
                    ("dwExtraInfo", ULONG_PTR)]

    class HARDWAREINPUT(ctypes.Structure):
        _fields_ = [("uMsg", ctypes.c_ulong), ("wParamL", ctypes.c_ushort),
                    ("wParamH", ctypes.c_ushort)]

    class _InputUnion(ctypes.Union):
        _fields_ = [("ki", KEYBDINPUT), ("mi", MOUSEINPUT), ("hi", HARDWAREINPUT)]

    class INPUT(ctypes.Structure):
        _fields_ = [("type", ctypes.c_ulong), ("u", _InputUnion)]


def send_key(scancode: int, down: bool) -> None:
    """Press or release a keyboard key by scan code (works in games)."""
    flags = 0x0008 | (0 if down else 0x0002)  # KEYEVENTF_SCANCODE | KEYEVENTF_KEYUP
    event = INPUT(type=1, u=_InputUnion(ki=KEYBDINPUT(0, scancode, flags, 0, 0)))
    ctypes.windll.user32.SendInput(1, ctypes.byref(event), ctypes.sizeof(event))


# ---------------------------------------------------------------- virtual pad
class Pad:
    """Wrapper around the virtual Xbox 360 pad. Falls back to a dry run
    (prints instead of controlling anything) if the pad can't be created."""

    def __init__(self) -> None:
        self.pad = None
        self.held_keys: set[str] = set()
        self._last_print = 0.0
        if vg is not None:
            try:
                self.pad = vg.VX360Gamepad()
                print("Virtual Xbox 360 controller created.")
            except Exception as exc:
                print(f"[warn] Could not create virtual controller ({exc})")
                print("       Is the ViGEmBus driver installed?")
        if self.pad is None:
            print("[warn] DRY RUN: inputs will be printed, not sent to any game.")

    @staticmethod
    def _button(name: str):
        return getattr(vg.XUSB_BUTTON, "XUSB_GAMEPAD_" + name)

    def axes(self, steer: float, throttle: float, brake: float) -> None:
        if self.pad is None:
            now = time.monotonic()
            if now - self._last_print > 0.25:
                self._last_print = now
                print(f"steer={steer:+.2f}  throttle={throttle:.2f}  brake={brake:.2f}")
            return
        self.pad.left_joystick_float(x_value_float=steer, y_value_float=0.0)
        self.pad.right_trigger_float(value_float=throttle)
        self.pad.left_trigger_float(value_float=brake)
        self.pad.update()

    def _key(self, key: str, down: bool) -> None:
        if sys.platform != "win32":
            print(f"key {key} {'down' if down else 'up'}")
            return
        send_key(SCANCODES[key], down)
        (self.held_keys.add if down else self.held_keys.discard)(key)

    def set_button(self, name: str, down: bool) -> None:
        if name.startswith("key:"):
            self._key(name[4:], down)
            return
        if self.pad is None:
            print(f"button {name} {'down' if down else 'up'}")
            return
        if down:
            self.pad.press_button(button=self._button(name))
        else:
            self.pad.release_button(button=self._button(name))
        self.pad.update()

    def reset(self) -> None:
        for key in list(self.held_keys):
            self._key(key, False)
        if self.pad is not None:
            self.pad.reset()
            self.pad.update()


pad = Pad()
tap_queue: "asyncio.Queue[str]"   # created at startup
rotary_last: dict[str, int] = {}  # last known detent per knob
last_input = 0.0                  # time of the last 'axes' message
active = False                    # True while the phone is driving the pad


def neutral() -> None:
    """Failsafe: release everything, centre the wheel."""
    global active
    active = False
    rotary_last.clear()
    pad.reset()
    print("Controls reset to neutral.")


def clamp(value: float, low: float, high: float) -> float:
    return max(low, min(high, value))


# ---------------------------------------------------------------- input handling
def handle_input(data: dict) -> None:
    global last_input, active
    kind = data.get("type")

    if kind == "axes":
        last_input = time.monotonic()
        active = True
        pad.axes(
            clamp(float(data["steer"]), -1.0, 1.0),
            clamp(float(data["throttle"]), 0.0, 1.0),
            clamp(float(data["brake"]), 0.0, 1.0),
        )

    elif kind == "btn":
        name = BUTTON_MAP.get(data["name"])
        if name:
            pad.set_button(name, bool(data["down"]))

    elif kind == "rot":
        knob, index = data["id"], int(data["index"])
        if knob not in ROTARY_MAP:
            return
        previous = rotary_last.get(knob)
        rotary_last[knob] = index
        if data.get("init") or previous is None:
            return                                   # just learning where the knob is
        step_up, step_down = ROTARY_MAP[knob]
        button = step_up if index > previous else step_down
        for _ in range(abs(index - previous)):
            tap_queue.put_nowait(button)


async def tap_worker() -> None:
    """Presses knob buttons one at a time so quick turns don't overlap."""
    while True:
        name = await tap_queue.get()
        pad.set_button(name, True)
        await asyncio.sleep(TAP_SECONDS)
        pad.set_button(name, False)
        await asyncio.sleep(TAP_SECONDS)


async def watchdog() -> None:
    """If the phone goes silent (screen off, Wi-Fi drop), stop the car."""
    while True:
        await asyncio.sleep(0.1)
        if active and time.monotonic() - last_input > WATCHDOG_SECONDS:
            neutral()


async def on_startup(app: web.Application) -> None:
    global tap_queue
    tap_queue = asyncio.Queue()
    app["tasks"] = [asyncio.create_task(tap_worker()), asyncio.create_task(watchdog())]


async def on_cleanup(app: web.Application) -> None:
    for task in app["tasks"]:
        task.cancel()
    pad.reset()


# ---------------------------------------------------------------- web routes
async def index(request: web.Request) -> web.FileResponse:
    return web.FileResponse(LAYOUT_FILE, headers={"Cache-Control": "no-store"})


async def ws_handler(request: web.Request) -> web.WebSocketResponse:
    ws = web.WebSocketResponse(heartbeat=5)
    await ws.prepare(request)
    print("Phone connected.")
    try:
        async for msg in ws:
            if msg.type == WSMsgType.TEXT:
                try:
                    handle_input(json.loads(msg.data))
                except (ValueError, KeyError, TypeError) as exc:
                    print(f"[warn] bad message ignored: {exc}")
    finally:
        print("Phone disconnected.")
        neutral()
    return ws


# ---------------------------------------------------------------- https
def local_ip() -> str:
    """Best guess at this PC's LAN address (the one the phone should open)."""
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    try:
        s.connect(("10.255.255.255", 1))  # no packet is actually sent
        return s.getsockname()[0]
    except OSError:
        return "127.0.0.1"
    finally:
        s.close()


def ensure_cert(ip: str) -> None:
    """Create a self-signed certificate on first run.
    If your PC's IP changes, delete cert.pem and key.pem and restart."""
    if CERT_FILE.exists() and KEY_FILE.exists():
        return
    key = rsa.generate_private_key(public_exponent=65537, key_size=2048)
    name = x509.Name([x509.NameAttribute(NameOID.COMMON_NAME, "f1-wheel")])
    now = datetime.datetime.now(datetime.timezone.utc)
    cert = (
        x509.CertificateBuilder()
        .subject_name(name)
        .issuer_name(name)
        .public_key(key.public_key())
        .serial_number(x509.random_serial_number())
        .not_valid_before(now - datetime.timedelta(days=1))
        .not_valid_after(now + datetime.timedelta(days=825))
        .add_extension(
            x509.SubjectAlternativeName(
                [x509.DNSName("localhost"), x509.IPAddress(ipaddress.ip_address(ip))]
            ),
            critical=False,
        )
        .sign(key, hashes.SHA256())
    )
    KEY_FILE.write_bytes(
        key.private_bytes(
            serialization.Encoding.PEM,
            serialization.PrivateFormat.TraditionalOpenSSL,
            serialization.NoEncryption(),
        )
    )
    CERT_FILE.write_bytes(cert.public_bytes(serialization.Encoding.PEM))
    print("Created new self-signed certificate.")


# ---------------------------------------------------------------- startup
def main() -> None:
    ip = local_ip()
    ensure_cert(ip)

    app = web.Application()
    app.add_routes([web.get("/", index), web.get("/ws", ws_handler)])
    app.on_startup.append(on_startup)
    app.on_cleanup.append(on_cleanup)

    ssl_ctx = ssl.SSLContext(ssl.PROTOCOL_TLS_SERVER)
    ssl_ctx.load_cert_chain(CERT_FILE, KEY_FILE)

    print(f"\nOpen this on your phone (same Wi-Fi):  https://{ip}:{PORT}")
    print("Accept the one-time 'not secure' warning - it's your own PC's certificate.\n")
    web.run_app(app, host="0.0.0.0", port=PORT, ssl_context=ssl_ctx, print=None)


if __name__ == "__main__":
    main()

"""F1 tracker backend. Setup: pip install flask fastf1 | Run: python main.py -> http://localhost:8000"""
import json, os, time
from datetime import datetime

import fastf1
import pandas as pd
from fastf1.ergast import Ergast
from flask import Flask, jsonify, request, send_from_directory

BASE = os.path.dirname(os.path.abspath(__file__))
os.makedirs(f"{BASE}/cache", exist_ok=True)
fastf1.Cache.enable_cache(f"{BASE}/cache")
app = Flask(__name__)
_c = {}


def cached(key, ttl, fn):
    h = _c.get(key)
    if h and time.time() - h[0] < ttl:
        return h[1]
    v = fn()
    _c[key] = (time.time(), v)
    return v


def recs(df):
    return json.loads(df.to_json(orient="records"))


def clean(o):
    return json.loads(json.dumps(o, default=lambda x: x.item() if hasattr(x, "item") else str(x)))


def yr():
    return request.args.get("year", datetime.now().year, type=int)


def secs(t):
    return None if pd.isna(t) else round(t.total_seconds(), 3)


def hms(t):
    if pd.isna(t):
        return ""
    m, s = divmod(t.total_seconds(), 60)
    h, m = divmod(m, 60)
    return f"{int(h)}:{int(m):02d}:{s:06.3f}" if h else f"{int(m)}:{s:06.3f}"


def sess(y, r):
    def load():
        x = fastf1.get_session(y, r, "R")
        x.load(weather=False, messages=False)
        return x
    return cached(("s", y, r), 86400, load)


def color(x, d):
    try:
        return "#" + x.results.loc[x.results["Abbreviation"] == d, "TeamColor"].iloc[0]
    except Exception:
        return "#e10600"


@app.route("/")
def index():
    return send_from_directory(BASE, "index.html")


@app.route("/api/schedule")
def schedule():
    y = yr()

    def load():
        now, out = pd.Timestamp.now(tz="UTC"), []
        for _, e in fastf1.get_event_schedule(y, include_testing=False).iterrows():
            ss = [{"name": e[f"Session{i}"], "time": pd.Timestamp(e[f"Session{i}DateUtc"]).tz_localize("UTC").isoformat()}
                  for i in range(1, 6) if isinstance(e.get(f"Session{i}"), str) and e.get(f"Session{i}") and pd.notna(e.get(f"Session{i}DateUtc"))]
            end = pd.Timestamp(ss[-1]["time"]) + pd.Timedelta(hours=3) if ss else None
            out.append({"round": int(e["RoundNumber"]), "name": e["EventName"], "location": e["Location"],
                        "country": e["Country"], "sessions": ss, "done": bool(end is not None and end < now)})
        return out
    return jsonify(cached(("sch", y), 3600, load))


@app.route("/api/standings")
def standings():
    y = yr()
    d = Ergast().get_driver_standings(season=y).content
    c = Ergast().get_constructor_standings(season=y).content
    dd = d[0].copy() if d else pd.DataFrame()
    if len(dd):
        dd["driver"] = dd["givenName"] + " " + dd["familyName"]
        dd["team"] = dd["constructorNames"].apply(", ".join)
        dd = dd[["position", "driver", "team", "points", "wins"]]
    return jsonify({"drivers": recs(dd) if len(dd) else [],
                    "constructors": recs(c[0][["position", "constructorName", "points", "wins"]]) if c else []})


@app.route("/api/results/<int:rnd>")
def results(rnd):
    r = Ergast().get_race_results(season=yr(), round=rnd, limit=100).content
    if not r:
        return jsonify([])
    df = r[0].copy()
    df["driver"] = df["givenName"] + " " + df["familyName"]
    df["time"] = df["Time"].apply(hms) if "Time" in df.columns else ""
    return jsonify(recs(df[["position", "driver", "constructorName", "grid", "laps", "time", "status", "points"]]))


@app.route("/api/circuits")
def circuits():
    df = Ergast().get_circuits(season=yr(), limit=100)
    return jsonify(recs(df[["circuitName", "locality", "country", "lat", "long", "circuitUrl"]]))


@app.route("/api/teams")
def teams():
    y = yr()
    ds = Ergast().get_driver_standings(season=y).content[0]
    cs = Ergast().get_constructor_standings(season=y).content[0]
    out = []
    for c in cs.itertuples():
        drv = [{"name": f"{d.givenName} {d.familyName}", "code": d.driverCode, "number": d.driverNumber,
                "points": d.points, "pos": d.position} for d in ds.itertuples() if c.constructorName in d.constructorNames]
        out.append({"pos": c.position, "name": c.constructorName, "nation": c.constructorNationality,
                    "points": c.points, "wins": c.wins, "url": c.constructorUrl, "drivers": drv})
    return jsonify(clean(out))


@app.route("/api/drivers/<int:rnd>")
def drivers(rnd):
    r = sess(yr(), rnd).results
    return jsonify([{"code": a, "name": n, "team": t} for a, n, t in zip(r["Abbreviation"], r["FullName"], r["TeamName"])])


@app.route("/api/laps/<int:rnd>")
def laps(rnd):
    x, out = sess(yr(), rnd), {}
    for d in request.args.get("drivers", "").split(",")[:4]:
        L = x.laps[x.laps["Driver"] == d]
        out[d] = {"color": color(x, d), "laps": [[int(n), secs(t)] for n, t in zip(L["LapNumber"], L["LapTime"]) if secs(t)]}
    return jsonify(out)


@app.route("/api/sectors/<int:rnd>")
def sectors(rnd):
    x = sess(yr(), rnd)
    g = x.laps.groupby("Driver")[["Sector1Time", "Sector2Time", "Sector3Time", "LapTime"]].min().reset_index()
    rows = [{"driver": r.Driver, "s1": secs(r.Sector1Time), "s2": secs(r.Sector2Time),
             "s3": secs(r.Sector3Time), "best": secs(r.LapTime)} for r in g.itertuples()]
    return jsonify(sorted(rows, key=lambda r: r["best"] or 1e9))


@app.route("/api/telemetry/<int:rnd>")
def telemetry(rnd):
    x, out = sess(yr(), rnd), {}
    for d in request.args.get("drivers", "").split(",")[:2]:
        lap = x.laps[x.laps["Driver"] == d].pick_fastest()
        t = lap.get_car_data().add_distance().iloc[::3]
        out[d] = {"color": color(x, d), "lapTime": secs(lap["LapTime"]), "dist": [round(v) for v in t["Distance"]],
                  "speed": list(map(int, t["Speed"])), "throttle": list(map(int, t["Throttle"])), "gear": list(map(int, t["nGear"]))}
    return jsonify(out)


@app.errorhandler(Exception)
def err(e):
    return jsonify({"error": str(e)}), 500


if __name__ == "__main__":
    app.run(debug=True, port=8000)

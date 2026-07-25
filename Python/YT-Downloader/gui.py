from PyQt6.QtWidgets import (
    QMainWindow,
    QWidget,
    QLabel,
    QLineEdit,
    QPushButton,
    QVBoxLayout,
)

from downloader import YouTubeDownloader

#--------------------------MAIN WINDOW---------------------------------
# Main window for the YouTube Downloader application.
class MainWindow(QMainWindow):
    def __init__(self):
        super().__init__()

        # Create downloader object
        self.downloader = YouTubeDownloader()

        # Window properties
        self.setWindowTitle("h0rizon Downloader")
        self.resize(900, 600)

        # Build the interface
        self.setup_ui()

        # Connect buttons/signals
        self.connect_signals()

#-------------------------SETUP UI---------------------------------
# Sets up the user interface by creating widgets and layouts.
    def setup_ui(self):
        """Create all widgets and layouts."""

        # Central widget
        central_widget = QWidget()
        self.setCentralWidget(central_widget)

        # Main layout
        layout = QVBoxLayout()
        central_widget.setLayout(layout)

        # Heading
        title = QLabel("🎬 h0rizon Downloader")
        layout.addWidget(title)

        # URL input
        self.url_input = QLineEdit()
        self.url_input.setPlaceholderText("Enter YouTube URL...")
        layout.addWidget(self.url_input)

        # Fetch button
        self.fetch_button = QPushButton("Get Video Info")
        layout.addWidget(self.fetch_button)

        # Status label
        self.status_label = QLabel("Waiting for URL...")
        layout.addWidget(self.status_label)

    def connect_signals(self):
        """Connect signals to slots."""

        self.fetch_button.clicked.connect(self.fetch_video)

#-----------------------FETCH VIDEO---------------------------------
# Fetches video information from the provided URL.
    def fetch_video(self):
        """Fetch information about the video."""
        url = self.url_input.text()

        if not url:
            self.status_label.setText("Please enter a URL.")
            return

        self.status_label.setText("Fetching video...")

        try:
            info = self.downloader.get_video_info(url)
            print("=" * 40)
            print("Title      :", info.get("title"))
            print("Uploader   :", info.get("uploader"))
            print("Duration   :", info.get("duration"))
            print("Views      :", info.get("view_count"))
            print("Resolution :", info.get("resolution"))
            print("=" * 40)

            self.status_label.setText("Video Loaded Successfully!")

        except Exception as e:
            self.status_label.setText("Error fetching video.")
            print(e)

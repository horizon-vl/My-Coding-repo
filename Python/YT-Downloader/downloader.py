import yt_dlp


class YouTubeDownloader:

    def get_video_info(self, url):
        options = {
            "quiet": True,
            "extract_flat": False,
        }

        with yt_dlp.YoutubeDL(options) as ydl:
            info = ydl.extract_info(url, download=False)

        return info

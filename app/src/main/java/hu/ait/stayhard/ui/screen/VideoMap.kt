package hu.ait.stayhard.ui.screen

data class Video(val index: Int, val category: VideoCategory, val url: String, val imageUrl: String)

enum class VideoCategory {
    Finance,
    Gym,
    Drugs,
    Procrastination
}

class VideoMap {
    fun mapVideos(): List<Video> {

        val videoData = listOf(
            Pair(
                "https://github.com/yobo1889/HabitTracker/raw/main/videoplayback.mp4",
                "https://github.com/yobo1889/HabitTracker/raw/main/DALL%C2%B7E%202023-12-11%2002.31.06%20-%20Motivational%20quote%20thumbnail%20with%20a%20light%20color%20scheme%2C%20primarily%20yellow%20and%20white.%20The%20quote%20is_%20_Don't%20give%20up%20on%20your%20dreams%2C%20keep%20sleeping!_%20The%20t.png"
            ),
            Pair(
                "https://github.com/yobo1889/HabitTracker/raw/main/WhatsApp%20Video%202023-12-10%20at%2023.53.55.mp4",
                "https://github.com/yobo1889/HabitTracker/raw/main/motivation_yellow.jpg"
            ),
            Pair(
                "https://github.com/yobo1889/HabitTracker/raw/main/WhatsApp%20Video%202023-12-10%20at%2023.54.24.mp4",
                "https://github.com/yobo1889/HabitTracker/raw/main/never_give_up.jpg"
            ),
            Pair(
                "https://github.com/yobo1889/HabitTracker/raw/main/WhatsApp%20Video%202023-12-10%20at%2023.55.14.mp4",
                "https://github.com/yobo1889/HabitTracker/raw/main/motivation_3.jpg"
            ),
            Pair(
                "https://github.com/yobo1889/HabitTracker/raw/main/WhatsApp%20Video%202023-12-10%20at%2023.56.05.mp4",
                "https://github.com/yobo1889/HabitTracker/raw/main/motivation_8.png"
            ),
        )

        // Map video URLs and image URLs to Video objects with categories
        return videoData.mapIndexed { index, (videoUrl, imageUrl) ->
            val category = when {
                videoUrl.contains("finance") -> VideoCategory.Finance
                videoUrl.contains("gym") -> VideoCategory.Gym
                videoUrl.contains("drugs") -> VideoCategory.Drugs
                videoUrl.contains("procrastination") -> VideoCategory.Procrastination
                else -> VideoCategory.Procrastination // Default to Procrastination if no category matches
            }

            Video(index + 1, category, videoUrl, imageUrl)
        }
    }
}

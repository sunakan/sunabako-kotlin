package demo.tutorial01

data class Album(
    val title: String,
    val artist: Artist,
    val links: List<String>,
    val songs: List<String>,
)
package suvorov.freetogame.domain.entity

data class Game(
    val id: Int?,
    val title: String?,
    val thumbnail: String?,
    val shortDescription: String?,
    val gameUrl: String?,
    val genre: String?,
    val platform: String?,
    val publisher: String?,
    val developer: String?,
    val releaseDate: String?,
    val profileUrl: String?,
    var isLibrary: Boolean = false
)
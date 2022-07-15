package suvorov.freetogame.data.mapper

import suvorov.freetogame.data.api.GameResponse
import suvorov.freetogame.domain.entity.Game

object GameResponseMapper {
    fun toGame(game: GameResponse): Game {
        return Game(
            game.id ?: 0,
            game.title,
            game.thumbnail,
            game.shortDescription,
            game.gameUrl,
            game.genre,
            game.platform,
            game.publisher,
            game.developer,
            game.releaseDate,
            game.profileUrl
        )
    }
}
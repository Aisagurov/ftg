package suvorov.freetogame.data.mapper

import suvorov.freetogame.data.database.GameEntity
import suvorov.freetogame.domain.entity.Game

object GameEntityMapper {
    fun toGame(game: GameEntity): Game {
        return Game(
            game.id,
            game.title,
            game.thumbnail,
            game.shortDescription,
            game.gameUrl,
            game.genre,
            game.platform,
            game.publisher,
            game.developer,
            game.releaseDate,
            game.profileUrl,
            game.isLibrary
        )
    }

    fun toGameEntity(game: Game): GameEntity {
        return GameEntity(
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
            game.profileUrl,
            game.isLibrary
        )
    }
}
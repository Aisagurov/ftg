package suvorov.freetogame.data.repository

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import suvorov.freetogame.data.database.GamesDao
import suvorov.freetogame.data.mapper.GameEntityMapper.toGame
import suvorov.freetogame.data.mapper.GameEntityMapper.toGameEntity
import suvorov.freetogame.domain.entity.Game
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val gamesDao: GamesDao): LocalDataSource {

    override fun getGames(): Observable<List<Game>> {
        return gamesDao.getGames().map {
            it.map { game ->
                toGame(game)
            }
        }
    }

    override fun getGameFromId(id: Int): Observable<Game> {
        return gamesDao.getGameFromId(id).map { game ->
            toGame(game)
        }
    }

    override fun getGamesLibrary(): Observable<List<Game>> {
        return gamesDao.getGamesLibrary().map {
            it.map { game ->
                toGame(game)
            }
        }
    }

    private fun saveOrUpdate(game: Game) {
        val id = gamesDao.insert(toGameEntity(game))
        if (id == (-1).toLong()) {
            gamesDao.update(toGameEntity(game))
        }
    }

    override fun saveGamesToDatabase(games: List<Game>): Completable {
        return Completable.fromAction {
            for(game in games) {
                saveOrUpdate(game)
            }
        }
    }

    override fun addGameToLibrary(game: Game): Completable {
        return Completable.fromAction {
            game.isLibrary = true
            saveOrUpdate(game)
        }
    }

    override fun removeGameFromLibrary(game: Game): Completable {
        return Completable.fromAction {
            game.isLibrary = false
            gamesDao.update(toGameEntity(game))
        }
    }
}
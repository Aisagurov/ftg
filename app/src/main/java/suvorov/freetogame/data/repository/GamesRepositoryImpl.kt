package suvorov.freetogame.data.repository

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import suvorov.freetogame.domain.entity.Game
import suvorov.freetogame.domain.repository.GamesRepository
import javax.inject.Inject

class GamesRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
    ): GamesRepository {

    override fun getGames(): Observable<List<Game>> {
        val localGamesObservable = localDataSource.getGames()
        val localFavoriteObservable = localDataSource.getGamesLibrary()

        val remoteGamesObservable = remoteDataSource.getGames()
            .zipWith(localFavoriteObservable) { remoteGames, localGames ->
                localGames.map { it.id }.forEach { localId ->
                    val game = remoteGames.find { remote -> remote.id == localId }
                    if (game != null) game.isLibrary = true
                }
                localDataSource.saveGamesToDatabase(remoteGames).subscribe()
                remoteGames
            }
        return localGamesObservable.flatMap { Observable.just(it.isNotEmpty()) }.flatMap {
            if (it) localGamesObservable
            else remoteGamesObservable
        }
    }

    override fun getGameFromId(id: Int): Observable<Game> {
        return localDataSource.getGameFromId(id)
    }

    override fun getGamesLibrary(): Observable<List<Game>> {
        return localDataSource.getGamesLibrary()
    }

    override fun addGameToLibrary(game: Game): Completable {
        return localDataSource.addGameToLibrary(game)
    }

    override fun removeGameFromLibrary(game: Game): Completable {
        return localDataSource.removeGameFromLibrary(game)
    }
}
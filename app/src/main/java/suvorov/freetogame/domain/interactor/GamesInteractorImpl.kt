package suvorov.freetogame.domain.interactor

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import suvorov.freetogame.domain.entity.Game
import suvorov.freetogame.domain.repository.GamesRepository
import javax.inject.Inject

class GamesInteractorImpl @Inject constructor(private val repository: GamesRepository): GamesInteractor {
    override fun getGames(): Observable<List<Game>> {
        return repository.getGames()
    }

    override fun getGameFromId(id: Int): Observable<Game> {
        return repository.getGameFromId(id)
    }

    override fun getGamesLibrary(): Observable<List<Game>> {
        return repository.getGamesLibrary()
    }

    override fun addGameToLibrary(game: Game): Completable {
        return repository.addGameToLibrary(game)
    }

    override fun removeGameFromLibrary(game: Game): Completable {
        return repository.removeGameFromLibrary(game)
    }

}
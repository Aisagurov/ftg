package suvorov.freetogame.domain.repository

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import suvorov.freetogame.domain.entity.Game

interface GamesRepository {

    fun getGames(): Observable<List<Game>>

    fun getGameFromId(id: Int): Observable<Game>

    fun getGamesLibrary(): Observable<List<Game>>

    fun addGameToLibrary(game: Game): Completable

    fun removeGameFromLibrary(game: Game): Completable
}
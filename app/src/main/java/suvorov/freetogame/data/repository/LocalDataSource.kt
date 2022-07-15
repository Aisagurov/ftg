package suvorov.freetogame.data.repository

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import suvorov.freetogame.domain.entity.Game

interface LocalDataSource {

    fun getGames(): Observable<List<Game>>

    fun getGameFromId(id: Int): Observable<Game>

    fun getGamesLibrary(): Observable<List<Game>>

    fun saveGamesToDatabase(games: List<Game>): Completable

    fun addGameToLibrary(game: Game): Completable

    fun removeGameFromLibrary(game: Game): Completable
}
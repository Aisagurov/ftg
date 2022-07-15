package suvorov.freetogame.data.repository

import io.reactivex.rxjava3.core.Observable
import suvorov.freetogame.domain.entity.Game

interface RemoteDataSource {
    fun getGames(): Observable<List<Game>>
}
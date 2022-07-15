package suvorov.freetogame.data.repository

import io.reactivex.rxjava3.core.Observable
import suvorov.freetogame.data.api.ApiService
import suvorov.freetogame.data.mapper.GameResponseMapper.toGame
import suvorov.freetogame.domain.entity.Game
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val service: ApiService): RemoteDataSource {

    override fun getGames(): Observable<List<Game>> {
        return service.getGames().map {
            it.map { game ->
                toGame(game)
            }
        }
    }
}
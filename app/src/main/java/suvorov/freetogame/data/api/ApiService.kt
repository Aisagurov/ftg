package suvorov.freetogame.data.api

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface ApiService {
    @GET("games")
    fun getGames(): Observable<List<GameResponse>>
}
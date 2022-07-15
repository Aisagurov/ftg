package suvorov.freetogame.data.database

import androidx.room.*
import io.reactivex.rxjava3.core.Observable

@Dao
interface GamesDao {
    @Query("SELECT * FROM game")
    fun getGames(): Observable<List<GameEntity>>

    @Query("SELECT * FROM game WHERE id = :id")
    fun getGameFromId(id: Int): Observable<GameEntity>

    @Query("SELECT * FROM game where isLibrary = 1")
    fun getGamesLibrary(): Observable<List<GameEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(game: GameEntity): Long

    @Update
    fun update(game: GameEntity): Int
}
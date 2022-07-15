package suvorov.freetogame.presentation.di.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import suvorov.freetogame.data.database.GamesDao
import suvorov.freetogame.data.database.GamesDatabase
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(context: Context): GamesDatabase {
        return Room.databaseBuilder(context, GamesDatabase::class.java, "GamesDatabase").build()
    }

    @Provides
    @Singleton
    fun provideDao(database: GamesDatabase): GamesDao {
        return database.gamesDao()
    }
}
package suvorov.freetogame.presentation.di.module

import dagger.Module
import dagger.Provides
import suvorov.freetogame.data.api.ApiService
import suvorov.freetogame.data.database.GamesDao
import suvorov.freetogame.data.repository.GamesRepositoryImpl
import suvorov.freetogame.data.repository.LocalDataSourceImpl
import suvorov.freetogame.data.repository.RemoteDataSourceImpl
import suvorov.freetogame.domain.repository.GamesRepository
import suvorov.freetogame.data.repository.LocalDataSource
import suvorov.freetogame.data.repository.RemoteDataSource
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideLocalDataSource(gamesDao: GamesDao): LocalDataSource {
        return LocalDataSourceImpl(gamesDao)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(service: ApiService): RemoteDataSource {
        return RemoteDataSourceImpl(service)
    }

    @Provides
    @Singleton
    fun provideGamesRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
        ): GamesRepository {

        return GamesRepositoryImpl(localDataSource, remoteDataSource)
    }
}
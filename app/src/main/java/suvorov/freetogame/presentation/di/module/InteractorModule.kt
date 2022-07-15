package suvorov.freetogame.presentation.di.module

import dagger.Module
import dagger.Provides
import suvorov.freetogame.domain.interactor.GamesInteractor
import suvorov.freetogame.domain.interactor.GamesInteractorImpl
import suvorov.freetogame.domain.repository.GamesRepository
import javax.inject.Singleton

@Module
class InteractorModule {
    @Provides
    @Singleton
    fun provideGamesInteractor(repository: GamesRepository): GamesInteractor {
        return GamesInteractorImpl(repository)
    }
}
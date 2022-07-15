package suvorov.freetogame.presentation.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import suvorov.freetogame.presentation.di.module.*
import suvorov.freetogame.presentation.ui.detail.DetailActivity
import suvorov.freetogame.presentation.ui.main.library.LibraryFragment
import suvorov.freetogame.presentation.ui.main.list.ListFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApiModule::class,
    ContextModule::class,
    DatabaseModule::class,
    RepositoryModule::class,
    InteractorModule::class
])

interface AppComponent {
    fun injectListFragment(fragment: ListFragment)
    fun injectLibraryFragment(fragment: LibraryFragment)
    fun injectDetailActivity(fragment: DetailActivity)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
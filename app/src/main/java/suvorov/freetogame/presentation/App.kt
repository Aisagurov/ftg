package suvorov.freetogame.presentation

import android.app.Application
import suvorov.freetogame.presentation.di.component.AppComponent
import suvorov.freetogame.presentation.di.component.DaggerAppComponent

class App: Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().application(this).build()
    }
}
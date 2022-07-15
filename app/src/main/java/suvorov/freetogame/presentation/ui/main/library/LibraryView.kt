package suvorov.freetogame.presentation.ui.main.library

import suvorov.freetogame.domain.entity.Game
import suvorov.freetogame.presentation.mvp.MvpView

interface LibraryView: MvpView {
    fun showGames(games: List<Game>)
    fun showMessage(text: String)
    fun showTextLibraryEmpty(show: Boolean)
}
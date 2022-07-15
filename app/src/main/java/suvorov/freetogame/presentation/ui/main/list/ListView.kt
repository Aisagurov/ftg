package suvorov.freetogame.presentation.ui.main.list

import suvorov.freetogame.domain.entity.Game
import suvorov.freetogame.presentation.mvp.MvpView

interface ListView: MvpView {
    fun showProgress(show: Boolean)
    fun showMessage(text: String)
    fun showErrorMessage(show: Boolean)
    fun showError(error: String)
    fun showGames(games: List<Game>)
}
package suvorov.freetogame.presentation.ui.detail

import suvorov.freetogame.domain.entity.Game
import suvorov.freetogame.presentation.mvp.MvpView

interface DetailView: MvpView {
    fun showGame(game: Game)
    fun showMessage(text: String)
}
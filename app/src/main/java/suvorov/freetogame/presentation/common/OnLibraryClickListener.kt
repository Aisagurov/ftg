package suvorov.freetogame.presentation.common

import suvorov.freetogame.domain.entity.Game

interface OnLibraryClickListener {
    fun onLibraryClick(game: Game)
}
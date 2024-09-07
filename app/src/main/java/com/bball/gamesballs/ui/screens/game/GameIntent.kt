package com.bball.gamesballs.ui.screens.game

sealed class GameIntent {
    data class UpdateEllipsePosition(val position: Float) : GameIntent()
    data object Pause : GameIntent()
    data object Resume : GameIntent()
    data object Restart : GameIntent()
}
//on back, sav score
//change ball

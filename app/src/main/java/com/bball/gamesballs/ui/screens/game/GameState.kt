package com.bball.gamesballs.ui.screens.game

import androidx.compose.ui.geometry.Offset

sealed class GameState {
    data class Running(
        val ballPosition: Offset,
        val ellipsePosition: Float
    ) : GameState()

    data class Paused(val lastRunningState: Running) : GameState()

    data class GameOver(
        val score: Int,
        val bestScore: Int
    ) : GameState()
}

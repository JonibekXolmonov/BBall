package com.bball.gamesballs.ui.screens.settings

sealed class SettingsIntent {
    data object SoundToggle: SettingsIntent()
    data object MusicToggle: SettingsIntent()
    data class BallSelected(val ballId:Int): SettingsIntent()
}
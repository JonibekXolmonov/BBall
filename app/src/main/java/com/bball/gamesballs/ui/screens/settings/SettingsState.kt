package com.bball.gamesballs.ui.screens.settings

import com.bball.gamesballs.R

data class SettingsState(
    val ballId: Int = R.drawable.football_ball,
    val sound: Boolean = false,
    val music: Boolean = false
)
package com.bball.gamesballs.ui.screens.settings

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.bball.gamesballs.helper.SharedPrefHelper
import com.bball.gamesballs.ui.screens.settings.SettingsIntent
import com.bball.gamesballs.ui.screens.settings.SettingsState

class SettingsViewModel(
    private val sharedPrefHelper: SharedPrefHelper
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state: StateFlow<SettingsState> = _state.asStateFlow()

    fun handleIntent(intent: SettingsIntent) {
        when (intent) {
            is SettingsIntent.BallSelected -> onBallSelected(intent.ballId)
            SettingsIntent.MusicToggle -> musicToggle()
            SettingsIntent.SoundToggle -> soundToggle()
        }
    }


    private fun onBallSelected(id: Int) {
        _state.value = _state.value.copy(ballId = id)
        saveSelected(id)

    }

    private fun soundToggle() {
        val currentSound = _state.value.sound
        _state.value = _state.value.copy(sound = !currentSound)
    }

    private fun musicToggle() {
        val currentMusic = _state.value.music
        _state.value = _state.value.copy(music = !currentMusic)
    }

    private fun saveSelected(id: Int) {
        sharedPrefHelper.saveBall(id)
    }

    private fun getSavedBall() {
        val ball = sharedPrefHelper.getBall()
        _state.value = _state.value.copy(ballId = ball)
    }

    init {
        getSavedBall()
    }
}
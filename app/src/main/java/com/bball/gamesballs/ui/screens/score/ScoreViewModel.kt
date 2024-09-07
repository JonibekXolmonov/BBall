package com.bball.gamesballs.ui.screens.score

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.bball.gamesballs.helper.KEY
import com.bball.gamesballs.helper.SharedPrefHelper

class ScoreViewModel(private val sharedPrefHelper: SharedPrefHelper) : ViewModel() {

    private val _scores = MutableStateFlow<List<Int>>(emptyList())
    val scores: StateFlow<List<Int>> get() = _scores.asStateFlow()

    private fun getList(key: String = KEY) {
        val topScores = sharedPrefHelper.getBestScores()

        _scores.value = topScores
    }

    init {
        getList()
    }
}
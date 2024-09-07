package com.bball.gamesballs.helper

import android.content.SharedPreferences
import com.bball.gamesballs.R

class SharedPrefHelper(private val sharedPreferences: SharedPreferences) {

    fun saveScore(key: String = KEY, score: Int) {
        val savedScores = getBestScores().toMutableList()
        if (score !in savedScores) {
            savedScores.add(score)
            savedScores.sortDescending()
        }
        val newBestScores = savedScores.take(6)

        val editor = sharedPreferences.edit()
        val stringList = newBestScores.joinToString(",")
        editor.putString(key, stringList)
        editor.apply()
    }

    fun getBestScores(key: String = KEY): List<Int> {
        val stringList = sharedPreferences.getString(key, null)
        return stringList?.split(",")?.map { it.toInt() } ?: emptyList()
    }

    fun saveBall(id: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(BALL_KEY, id)
        editor.apply()
    }

    fun getBall(): Int {
        return sharedPreferences.getInt(BALL_KEY, R.drawable.football_ball)
    }
}

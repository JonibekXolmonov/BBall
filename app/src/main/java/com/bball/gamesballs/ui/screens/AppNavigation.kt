package com.bball.gamesballs.ui.screens

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.bball.gamesballs.ui.screens.game.gameScreen
import com.bball.gamesballs.ui.screens.game.navigateToGame
import com.bball.gamesballs.ui.screens.home.HOME_ROUTE
import com.bball.gamesballs.ui.screens.home.homeScreen
import com.bball.gamesballs.ui.screens.score.navigateToScore
import com.bball.gamesballs.ui.screens.score.scoreScreen
import com.bball.gamesballs.ui.screens.settings.navigateToSettings
import com.bball.gamesballs.ui.screens.settings.settingsScreen

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    sharedPreferences: SharedPreferences
) {

    NavHost(navController = navController, modifier = modifier, startDestination = HOME_ROUTE) {
        homeScreen(
            onPlayPressed = navController::navigateToGame,
            onScorePressed = navController::navigateToScore,
            onSettingsPressed = navController::navigateToSettings
        )

        gameScreen(
            onHomeBack = navController::popBackStack,
            sharedPreferences = sharedPreferences
        )

        settingsScreen(
            sharedPreferences = sharedPreferences,
            onBackPressed = navController::popBackStack
        )

        scoreScreen(
            onBackPressed = navController::popBackStack,
            sharedPreferences = sharedPreferences
        )
    }
}
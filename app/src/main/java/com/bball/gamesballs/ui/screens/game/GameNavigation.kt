package com.bball.gamesballs.ui.screens.game

import android.content.SharedPreferences
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val GAME_ROUTE = "game_route"


fun NavController.navigateToGame(navOptions: NavOptions? = null) = navigate(GAME_ROUTE, navOptions)

fun NavGraphBuilder.gameScreen(
    onHomeBack: () -> Unit,
    sharedPreferences: SharedPreferences
) {
    composable(
        route = GAME_ROUTE
    ) {
        GameScreenRoute(
            onHomeBack = onHomeBack,
            sharedPreferences = sharedPreferences
        )
    }
}
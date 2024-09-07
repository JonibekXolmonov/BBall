package com.bball.gamesballs.ui.screens.score

import android.content.SharedPreferences
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val SCORE_ROUTE = "score_route"


fun NavController.navigateToScore(navOptions: NavOptions? = null) =
    navigate(SCORE_ROUTE, navOptions)

fun NavGraphBuilder.scoreScreen(
    onBackPressed: () -> Unit,
    sharedPreferences: SharedPreferences
) {
    composable(
        route = SCORE_ROUTE
    ) {
        ScoreScreenRoute(
            sharedPreferences = sharedPreferences,
            onBackPressed = onBackPressed
        )
    }
}
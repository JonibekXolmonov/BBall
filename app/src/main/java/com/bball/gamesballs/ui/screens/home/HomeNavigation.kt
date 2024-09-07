package com.bball.gamesballs.ui.screens.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val HOME_ROUTE = "home_route"


fun NavGraphBuilder.homeScreen(
    onPlayPressed: () -> Unit,
    onScorePressed: () -> Unit,
    onSettingsPressed: () -> Unit,
) {
    composable(
        route = HOME_ROUTE
    ) {
        HomeScreenRoute(
            onPlayPressed = onPlayPressed,
            onScorePressed = onScorePressed,
            onSettingsPressed = onSettingsPressed
        )
    }
}
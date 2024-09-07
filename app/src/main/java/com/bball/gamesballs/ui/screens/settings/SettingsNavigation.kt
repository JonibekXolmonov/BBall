package com.bball.gamesballs.ui.screens.settings

import android.content.SharedPreferences
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val SETTINGS_ROUTE = "settings_route"

fun NavController.navigateToSettings(navOptions: NavOptions? = null) = navigate(SETTINGS_ROUTE, navOptions)

fun NavGraphBuilder.settingsScreen(
    sharedPreferences: SharedPreferences,
    onBackPressed:()->Unit
) {
    composable(
        route = SETTINGS_ROUTE
    ) {
        SettingsScreenRoute(
            sharedPreferences = sharedPreferences,
            onBackPressed = onBackPressed
        )
    }
}
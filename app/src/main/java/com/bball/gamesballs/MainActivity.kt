package com.bball.gamesballs

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.bball.gamesballs.ui.screens.AppNavigation
import com.bball.gamesballs.ui.theme.BBallTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val sharedPreferences = getSharedPreferences("App_shared_pred", Context.MODE_PRIVATE)
        setContent {
            val navController = rememberNavController()
            BBallTheme {
                AppNavigation(
                    navController = navController,
                    sharedPreferences = sharedPreferences,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
package com.bball.gamesballs.ui.screens.game

import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.bball.gamesballs.ui.screens.AppBackground
import com.bball.gamesballs.ui.screens.AppIconButton
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bball.gamesballs.R
import com.bball.gamesballs.helper.GameViewModelFactory
import com.bball.gamesballs.helper.SharedPrefHelper
import com.bball.gamesballs.ui.screens.dialog.GameOverDialog
import com.bball.gamesballs.ui.screens.dialog.PauseDialog

@Composable
fun GameScreenRoute(
    sharedPreferences: SharedPreferences,
    onHomeBack: () -> Unit,
    viewModel: GameViewModel = viewModel(
        factory = GameViewModelFactory(
            sharedPrefHelper = SharedPrefHelper(sharedPreferences)
        )
    ),
    density: Density = LocalDensity.current,
    configuration: Configuration = LocalConfiguration.current
) {

    val gameState by viewModel.state
    val score by viewModel.score
    val ball by viewModel.ball
    val isBallCatch by viewModel.isBallCatch
    val isBallMissed by viewModel.isBallMissed

    // Update ViewModel with screen dimensions
    LaunchedEffect(true) {
        val screenHeight = configuration.screenHeightDp.dp
        val screenWidth = configuration.screenWidthDp.dp
        val ellipseWidthPx: Float
        val ballRadiusPx: Float
        val heightPx: Float
        val widthPx: Float

        with(density) {
            ellipseWidthPx = 144.dp.toPx()
            ballRadiusPx = 39.dp.toPx()
            heightPx = screenHeight.toPx()
            widthPx = screenWidth.toPx()
        }

        viewModel.setScreenDimensions(widthPx, heightPx, ellipseWidthPx, ballRadiusPx)
    }

    when (gameState) {
        is GameState.GameOver -> GameOverDialog(
            score = score,
            bestScore = (gameState as GameState.GameOver).bestScore,
            onRestart = {
                viewModel.handleIntent(GameIntent.Restart)
            },
            onHomeBack = onHomeBack
        )

        is GameState.Paused -> PauseDialog(
            onContinue = {
                viewModel.handleIntent(GameIntent.Resume)
            },
            onMenu = onHomeBack
        )

        is GameState.Running -> {
            GameScreen(
                state = gameState as GameState.Running,
                score = score,
                ball = ball,
                isBallCatch = isBallCatch,
                isBallMissed = isBallMissed,
                onHomeBack = {
                    viewModel.saveScore()
                    onHomeBack()
                },
                onPauseGame = {
                    viewModel.handleIntent(GameIntent.Pause)
                },
                onPositionChanged = { newPosition ->
                    viewModel.handleIntent(GameIntent.UpdateEllipsePosition(newPosition))
                },
            )
        }
    }
}

@Composable
fun GameScreen(
    state: GameState.Running,
    isBallCatch: Boolean,
    isBallMissed: Boolean,
    score: Int,
    ball: Int,
    onHomeBack: () -> Unit,
    onPauseGame: () -> Unit,
    onPositionChanged: (Float) -> Unit,
) {
    AppBackground(screenPaddingValues = PaddingValues()) {

        GameTop(
            onHomeBack = onHomeBack,
            score = score,
            onPauseGame = onPauseGame,
        )

        GameLayout(
            modifier = Modifier
                .fillMaxSize(),
            ballPosition = state.ballPosition,
            ellipsePosition = state.ellipsePosition,
            ball = ball,
            isBallCatch = isBallCatch,
            isBallMissed = isBallMissed,
            onEllipseDrag = onPositionChanged
        )
    }
}

@Composable
fun GameTop(
    score: Int,
    onHomeBack: () -> Unit,
    onPauseGame: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
        ) {
            AppIconButton(
                iconId = R.drawable.home,
                onClick = onHomeBack
            )

            Spacer(modifier = Modifier.weight(1f))

            AppIconButton(
                iconId = R.drawable.pause,
                onClick = onPauseGame
            )
        }

        Text(
            text = score.toString(),
            style = MaterialTheme.typography.titleLarge
                .copy(Color.White)
        )
    }
}

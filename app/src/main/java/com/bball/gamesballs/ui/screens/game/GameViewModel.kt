package com.bball.gamesballs.ui.screens.game

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import com.bball.gamesballs.helper.SharedPrefHelper
import kotlin.random.Random


class GameViewModel(
    private val sharedPrefHelper: SharedPrefHelper
) : ViewModel() {

    private val _state = mutableStateOf<GameState>(
        GameState.Running(
            ballPosition = Offset(0f, 0f),
            ellipsePosition = 0f,
        )
    )
    val state: State<GameState> = _state

    private val _score = mutableIntStateOf(0)
    val score = _score

    private val _ball = mutableIntStateOf(0)
    val ball = _ball

    val isBallCatch = mutableStateOf(false)
    val isBallMissed = mutableStateOf(false)

    private var gameJob: Job? = null
    private var screenWidth: Float = 800f // Default, will be updated
    private var screenHeight: Float = 1000f // Default, will be updated
    private var ellipseWidth: Float = 200f // Default, will be updated
    private var ballRadius: Float = 50f // Default, will be updated
    private var bestscore: Int = 0

    fun setScreenDimensions(width: Float, height: Float, ellipseWidth: Float, ballRadius: Float) {
        screenWidth = width
        screenHeight = height
        this.ellipseWidth = ellipseWidth
        this.ballRadius = ballRadius

        if (_state.value is GameState.Running && (_state.value as GameState.Running).ballPosition == Offset.Zero) {
            restartGame()
        }
    }

    private fun onBallCaught() {
        isBallCatch.value = true

        // Launch a coroutine to reset the value after 1 second
        viewModelScope.launch {
            delay(1000L) // Wait for 1 second
            isBallCatch.value = false
        }
    }

    private fun onBallMissed() {
        isBallMissed.value = true
        viewModelScope.launch {
            delay(500)
            _state.value = GameState.GameOver(_score.intValue, bestScore = bestscore)
            isBallMissed.value = false
        }
    }


    private fun startGame() {
        gameJob?.cancel()
        gameJob = viewModelScope.launch {
            while (isActive) {
                delay(16L) // ~60 FPS
                updateBallPosition()
            }
        }
    }

    private fun updateBallPosition() {
        val currentState = _state.value
        if (currentState is GameState.Running) {
            val newY = currentState.ballPosition.y + 10f
            val newPosition = Offset(currentState.ballPosition.x, newY)

            // Check if the ball has reached the bottom
            if (newY >= screenHeight - 126f) { //126f is ellipse bottom margin
                checkCollision(newPosition, currentState.ellipsePosition)
            } else {
                _state.value = currentState.copy(ballPosition = newPosition)
            }
        }
    }

    private fun checkCollision(ballPosition: Offset, ellipsePosition: Float) {
        val ellipseLeft = ellipsePosition
        val ellipseRight = ellipsePosition + ellipseWidth

        // Check collision with ellipse
        if (ballPosition.x >= ellipseLeft && ballPosition.x + 2 * ballRadius <= ellipseRight) {
            // Ball is caught; reset to top with a new random x position

            val newX =
                Random.nextFloat() * (screenWidth - 2 * ballRadius) // Ensure ball stays within bounds
            _state.value = GameState.Running(
                ballPosition = Offset(newX, 0f),
                ellipsePosition = ellipsePosition
            )
            _score.intValue += 1
            onBallCaught()
        } else {
            // Game Over
            gameJob?.cancel()
            onBallMissed()
            saveScore()
        }
    }

    fun handleIntent(intent: GameIntent) {
        when (intent) {
            is GameIntent.UpdateEllipsePosition -> updateEllipsePosition(intent.position)
            is GameIntent.Pause -> pauseGame()
            is GameIntent.Resume -> resumeGame()
            is GameIntent.Restart -> restartGame()
        }
    }

    private fun updateEllipsePosition(position: Float) {
        val currentState = _state.value
        if (currentState is GameState.Running) {
            // Clamp the position within screen bounds
            val clampedPosition =
                (currentState.ellipsePosition + position).coerceIn(0f, screenWidth - ellipseWidth)
            _state.value =
                currentState.copy(ellipsePosition = clampedPosition)
        }
    }

    private fun pauseGame() {
        val currentState = _state.value
        if (currentState is GameState.Running) {
            gameJob?.cancel()
            _state.value = GameState.Paused(lastRunningState = currentState)
        }
    }

    private fun resumeGame() {
        val currentState = _state.value
        if (currentState is GameState.Paused) {
            _state.value = currentState.lastRunningState
            startGame()
        }
    }

    private fun restartGame() {
        val newX = Random.nextFloat() * (screenWidth - ballRadius)
        val initialEllipsePosition = (screenWidth - ellipseWidth) / 2 // Centered
        _state.value = GameState.Running(
            ballPosition = Offset(newX, 0f),
            ellipsePosition = initialEllipsePosition
        )
        _score.intValue = 0
        isBallMissed.value = false
        bestscore = with(sharedPrefHelper.getBestScores()) {
            if (isEmpty()) 0 else first()
        }
        startGame()
    }

    fun saveScore() {
        if (_score.intValue > 0) {
            sharedPrefHelper.saveScore(score = _score.intValue)
        }
    }

    init {
        _ball.intValue = sharedPrefHelper.getBall()
    }
}
package com.bball.gamesballs.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bball.gamesballs.R
import com.bball.gamesballs.ui.screens.AppBackground
import com.bball.gamesballs.ui.screens.OutlinedColumnButton
import com.bball.gamesballs.ui.theme.mainRed

@Composable
fun HomeScreenRoute(
    onPlayPressed: () -> Unit,
    onScorePressed: () -> Unit,
    onSettingsPressed: () -> Unit,
) {

    HomeScreen(
        onSettingsPressed = onSettingsPressed,
        onScorePressed = onScorePressed,
        onPlayPressed = onPlayPressed
    )
}

@Composable
fun HomeScreen(
    onPlayPressed: () -> Unit,
    onScorePressed: () -> Unit,
    onSettingsPressed: () -> Unit,
) {
    AppBackground(
        screenPaddingValues = PaddingValues(horizontal = 24.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {
            Ball()

            PlayButton(modifier = Modifier.fillMaxWidth(), onClick = onPlayPressed)

            ActionButtons(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                onScorePressed = onScorePressed,
                onSettingsPressed = onSettingsPressed
            )
        }
    }
}

@Composable
fun Ball(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null
        )

        Column {
            Image(
                painter = painterResource(id = R.drawable.thunder),
                contentDescription = null
            )

            Image(
                painter = painterResource(id = R.drawable.ball_text),
                contentDescription = null
            )
        }
    }
}

@Composable
fun PlayButton(
    modifier: Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        contentPadding = PaddingValues(vertical = 16.dp),
        colors = ButtonDefaults.buttonColors()
            .copy(
                containerColor = mainRed,
                contentColor = Color.White
            ),
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.play),
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
fun ActionButtons(
    modifier: Modifier = Modifier,
    onScorePressed: () -> Unit,
    onSettingsPressed: () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedColumnButton(
            iconIds = intArrayOf(R.drawable.score),
            labelId = R.string.score,
            onClick = onScorePressed,
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight()
        )

        OutlinedColumnButton(
            iconIds = intArrayOf(R.drawable.polygon, R.drawable.settings),
            labelId = R.string.settings,
            onClick = onSettingsPressed,
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight()
        )
    }
}
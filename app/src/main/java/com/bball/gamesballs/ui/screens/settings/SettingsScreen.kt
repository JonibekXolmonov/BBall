package com.bball.gamesballs.ui.screens.settings

import android.content.SharedPreferences
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bball.gamesballs.ui.screens.AppBackground
import com.bball.gamesballs.ui.screens.AppSwitch
import com.bball.gamesballs.ui.screens.AppTopBar
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bball.gamesballs.R
import com.bball.gamesballs.helper.SettingsViewModelFactory
import com.bball.gamesballs.helper.SharedPrefHelper
import com.bball.gamesballs.ui.theme.mainGrey
import com.bball.gamesballs.ui.theme.mainYellow

@Composable
fun SettingsScreenRoute(
    onBackPressed: () -> Unit,
    sharedPreferences: SharedPreferences,
    viewModel: SettingsViewModel = viewModel(
        factory = SettingsViewModelFactory(
            sharedPrefHelper = SharedPrefHelper(sharedPreferences)
        )
    )
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    SettingsScreen(
        state = state,
        onBallSelected = {
            viewModel.handleIntent(SettingsIntent.BallSelected(it))
        },
        onSoundValueChange = {
            viewModel.handleIntent(SettingsIntent.SoundToggle)
        },
        onMusicValueChange = {
            viewModel.handleIntent(SettingsIntent.MusicToggle)
        },
        onBackPressed = onBackPressed
    )
}

@Composable
fun SettingsScreen(
    state: SettingsState,
    onBallSelected: (Int) -> Unit,
    onSoundValueChange: (Boolean) -> Unit,
    onMusicValueChange: (Boolean) -> Unit,
    onBackPressed: () -> Unit
) {
    AppBackground(screenPaddingValues = PaddingValues(horizontal = 24.dp)) {

        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            AppTopBar(
                labelId = R.string.settings,
                onBackPressed = onBackPressed
            )

            AppSwitch(
                value = state.sound,
                labelId = R.string.sound,
                modifier = Modifier.padding(top = 12.dp),
                onValueChange = onSoundValueChange
            )

            AppSwitch(
                value = state.music,
                labelId = R.string.music,
                onValueChange = onMusicValueChange
            )


            BallSelection(
                selectedBallId = state.ballId,
                onBallSelected = onBallSelected
            )
        }
    }
}


@Composable
fun BallSelection(
    modifier: Modifier = Modifier,
    selectedBallId: Int,
    onBallSelected: (Int) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(id = R.string.ball),
            style = MaterialTheme.typography.titleMedium
                .copy(White)
        )

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            BallSelector(
                modifier = Modifier.weight(1f),
                ballId = R.drawable.basketball_ball,
                isBallSelected = selectedBallId == R.drawable.basketball_ball,
                onBallSelected = onBallSelected
            )

            BallSelector(
                modifier = Modifier.weight(1f),
                ballId = R.drawable.football_ball,
                isBallSelected = selectedBallId == R.drawable.football_ball,
                onBallSelected = onBallSelected
            )
        }
    }
}

@Composable
fun BallSelector(
    modifier: Modifier = Modifier,
    @DrawableRes ballId: Int,
    isBallSelected: Boolean,
    onBallSelected: (Int) -> Unit
) {

    val border = BorderStroke(color = if (isBallSelected) mainYellow else mainGrey, width = 2.dp)

    Button(
        modifier = modifier,
        onClick = {
            onBallSelected(ballId)
        },
        contentPadding = PaddingValues(vertical = 16.dp),
        shape = MaterialTheme.shapes.medium,
        border = border,
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = Color.Black
        )
    ) {
        Image(
            painter = painterResource(id = ballId),
            contentDescription = null
        )
    }
}
package com.bball.gamesballs.ui.screens.dialog

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bball.gamesballs.R
import com.bball.gamesballs.ui.screens.AppIconButton
import com.bball.gamesballs.ui.theme.BBallTheme
import com.bball.gamesballs.ui.theme.mainRed
import com.bball.gamesballs.ui.theme.mainYellow
import com.bball.gamesballs.ui.theme.onBlack

@Composable
fun GameOverDialog(
    score: Int,
    bestScore: Int,
    onRestart: () -> Unit,
    onHomeBack: () -> Unit
) {
    Dialog(
        onDismissRequest = { },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        GameOverContent(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp)
                .background(Color.Transparent),
            score = score,
            bestScore = bestScore,
            onRestart = onRestart,
            onHomeBack = onHomeBack
        )
    }
}

@Composable
fun GameOverContent(
    modifier: Modifier = Modifier,
    score: Int,
    bestScore: Int,
    onRestart: () -> Unit,
    onHomeBack: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppIconButton(
            modifier = Modifier
                .align(Alignment.Start), iconId = R.drawable.home,
            onClick = onHomeBack
        )

        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(R.string.game_over),
            style = MaterialTheme.typography.labelLarge
                .copy(mainRed)
        )

        Box(
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium)
                .background(onBlack)
                .padding(vertical = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                ScoreBest(labelId = R.string.score, value = score)
                ScoreBest(labelId = R.string.best, value = bestScore)
            }
        }

        RestartButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            onClick = onRestart
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun ScoreBest(
    @StringRes labelId: Int,
    value: Int
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = stringResource(labelId),
            style = MaterialTheme.typography.labelMedium
                .copy(Color.White)
        )
        Text(
            text = value.toString(),
            style = MaterialTheme.typography.labelLarge
                .copy(mainYellow)
        )
    }
}

@Composable
fun RestartButton(
    modifier: Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        contentPadding = PaddingValues(vertical = 16.dp),
        colors = ButtonDefaults.buttonColors()
            .copy(
                containerColor = mainYellow,
                contentColor = Color.Black
            ),
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.restart),
            contentDescription = null,
            tint = Color.Black
        )
    }
}

@Preview
@Composable
private fun GameOverDialogPr() {
    BBallTheme  {
        GameOverDialog(6, 23, {}, {})
    }
}
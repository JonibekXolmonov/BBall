package com.bball.gamesballs.ui.screens.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bball.gamesballs.R
import com.bball.gamesballs.ui.theme.BBallTheme
import com.bball.gamesballs.ui.theme.mainYellow
import com.bball.gamesballs.ui.theme.onBlack

@Composable
fun PauseDialog(
    onContinue: () -> Unit,
    onMenu: () -> Unit
) {
    Dialog(
        onDismissRequest = {  },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        PauseDialogContent(
            Modifier
                .fillMaxWidth()
                .background(Color.Transparent),
            onContinue, onMenu
        )
    }
}

@Composable
fun PauseDialogContent(
    modifier: Modifier = Modifier,
    onContinue: () -> Unit,
    onMenu: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = stringResource(R.string.pause),
            style = MaterialTheme.typography.labelLarge
                .copy(mainYellow)
        )

        Box(
            modifier = Modifier
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
                ClickableText(
                    text = AnnotatedString(stringResource(R.string.str_continue)),
                    style = MaterialTheme.typography.labelMedium
                        .copy(Color.White),
                    onClick = {
                        onContinue()
                    }
                )

                ClickableText(
                    text = AnnotatedString(stringResource(R.string.menu)),
                    style = MaterialTheme.typography.labelMedium
                        .copy(Color.White),
                    onClick = {
                        onMenu()
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun PauseDialogPr() {
    BBallTheme {
        PauseDialog({}, {})
    }
}
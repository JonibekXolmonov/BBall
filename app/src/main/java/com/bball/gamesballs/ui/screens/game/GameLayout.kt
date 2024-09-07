package com.bball.gamesballs.ui.screens.game

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.bball.gamesballs.R
import com.bball.gamesballs.ui.theme.mainYellow
import kotlin.math.roundToInt

@Composable
fun GameLayout(
    modifier: Modifier,
    ballPosition: Offset,
    ellipsePosition: Float,
    isBallCatch: Boolean,
    isBallMissed: Boolean,
    onEllipseDrag: (Float) -> Unit,
    ball: Int
) {

    Box(modifier = modifier) {
        Ball(
            ball=ball,
            position = ballPosition,
            isBallMissed = isBallMissed
        )

        Basket(
            modifier = Modifier
                .padding(bottom = 48.dp)
                .align(Alignment.BottomStart),
            position = ellipsePosition,
            isBallCatch = isBallCatch,
            onDrag = onEllipseDrag
        )
    }
}

@Composable
fun Ball(
    position: Offset,
    isBallMissed: Boolean,
    ball: Int
) {

    if (isBallMissed) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .offset { IntOffset(position.x.roundToInt(), position.y.roundToInt()) }
        ) {
            Image(
                painter = painterResource(id = ball),
                contentDescription = null,
                modifier = Modifier
                    .size(78.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.close),
                contentDescription = null,
                modifier = Modifier.size(42.dp)
            )
        }
    } else {
        Image(
            painter = painterResource(id = ball),
            contentDescription = null,
            modifier = Modifier
                .size(78.dp)
                .offset { IntOffset(position.x.roundToInt(), position.y.roundToInt()) }
        )
    }
}

@Composable
fun Basket(
    modifier: Modifier = Modifier,
    position: Float,
    isBallCatch: Boolean,
    onDrag: (Float) -> Unit
) {
    Box(
        modifier = modifier
            .offset(x = with(LocalDensity.current) { position.toDp() })
            .size(144.dp, 48.dp)
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    onDrag(delta)
                }
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.ellipse),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        Animation(visible = isBallCatch) {
            Text(
                text = stringResource(R.string._1),
                style = MaterialTheme.typography.labelMedium.copy(color = mainYellow),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 4.dp)
            )
        }
    }
}

@Composable
fun Animation(visible: Boolean, content: @Composable AnimatedVisibilityScope.() -> Unit) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut(),
        content = content
    )
}


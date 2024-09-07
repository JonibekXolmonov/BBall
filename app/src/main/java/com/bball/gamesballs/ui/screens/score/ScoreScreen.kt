package com.bball.gamesballs.ui.screens.score

import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bball.gamesballs.ui.screens.AppBackground
import com.bball.gamesballs.ui.screens.AppTopBar
import com.bball.gamesballs.ui.screens.ext.blurredShadow
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bball.gamesballs.R
import com.bball.gamesballs.helper.ScoreViewModelFactory
import com.bball.gamesballs.helper.SharedPrefHelper
import com.bball.gamesballs.ui.theme.mainYellow
import com.bball.gamesballs.ui.theme.onBlack

@Composable
fun ScoreScreenRoute(
    onBackPressed: () -> Unit,
    sharedPreferences: SharedPreferences,
    viewModel: ScoreViewModel = viewModel(
        factory = ScoreViewModelFactory(
            sharedPrefHelper = SharedPrefHelper(sharedPreferences)
        )
    )
) {

    val scores by viewModel.scores.collectAsStateWithLifecycle()

    ScoreScreen(
        onBackPressed = onBackPressed,
        scores = scores
    )
}

@Composable
fun ScoreScreen(
    onBackPressed: () -> Unit,
    scores: List<Int>
) {
    AppBackground(screenPaddingValues = PaddingValues(bottom = 0.dp)) {

        Image(
            painter = painterResource(id = R.drawable.big_polygon),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {

            AppTopBar(
                labelId = R.string.score,
                onBackPressed = onBackPressed
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(top = 36.dp, bottom = 24.dp)
            ) {
                itemsIndexed(scores) { index, score ->
                    ScoreItem(
                        score = score,
                        container = if (index <= 2) mainYellow else onBlack,
                        content = if (index <= 2) Black else White
                    )
                }
            }
        }
    }
}

@Composable
fun ScoreItem(
    score: Int,
    container: Color,
    content: Color,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .blurredShadow()
            .clip(MaterialTheme.shapes.medium)
            .background(container)
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = score.toString(),
            style = MaterialTheme.typography.labelLarge
                .copy(content)
        )
    }
}
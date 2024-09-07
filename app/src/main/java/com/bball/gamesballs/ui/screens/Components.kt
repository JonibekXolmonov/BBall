@file:OptIn(ExperimentalMaterial3Api::class)

package com.bball.gamesballs.ui.screens

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.bball.gamesballs.R
import com.bball.gamesballs.ui.theme.mainGrey
import com.bball.gamesballs.ui.theme.mainYellow
import com.bball.gamesballs.ui.screens.ext.blurredShadow

@Composable
fun OutlinedColumnButton(
    modifier: Modifier = Modifier,
    @DrawableRes vararg iconIds: Int,
    @StringRes labelId: Int,
    onClick: () -> Unit
) {
    androidx.compose.material3.OutlinedButton(
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(2.dp, mainGrey),
        contentPadding = PaddingValues(vertical = 8.dp),
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.height(50.dp)) {
                iconIds.forEachIndexed { index, iconId ->
                    Image(
                        painter = painterResource(
                            id = iconId
                        ), contentDescription = stringResource(id = labelId),
                        modifier = Modifier
                            .padding(start = if (index == 0 && iconIds.size > 1) 24.dp else 0.dp)
                    )
                }
            }

            Text(
                text = stringResource(id = labelId),
                style = MaterialTheme.typography.labelSmall
                    .copy(color = White)
            )
        }
    }
}

@Composable
fun AppBackground(
    screenPaddingValues: PaddingValues,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
            .padding(screenPaddingValues),
    ) {
        content()
    }
}

@Composable
fun AppTopBar(
    @StringRes labelId: Int,
    onBackPressed: () -> Unit
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors().copy(
            containerColor = Color.Transparent,
            navigationIconContentColor = White,
            titleContentColor = White,
            actionIconContentColor = Color.Transparent
        ),
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.arrow_left),
                    contentDescription = null,
                )
            }
        },
        title = {
            Text(
                text = stringResource(id = labelId),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.blurredShadow()
            )
        }
    )
}

@Composable
fun AppSwitch(
    modifier: Modifier = Modifier,
    @StringRes labelId: Int,
    value: Boolean,
    onValueChange: (Boolean) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(id = labelId),
            style = MaterialTheme.typography.titleMedium
                .copy(White)
        )

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SwitchButton(
                modifier = Modifier.weight(1f),
                labelId = R.string.on,
                isSelected = value,
                onClick = {
                    onValueChange(true)
                })

            SwitchButton(
                modifier = Modifier.weight(1f),
                labelId = R.string.off,
                isSelected = !value,
                onClick = {
                    onValueChange(false)
                })
        }
    }
}

@Composable
fun SwitchButton(
    modifier: Modifier,
    @StringRes labelId: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {

    val container = if (isSelected) mainYellow else Black
    val content = if (isSelected) Black else mainGrey
    val border = if (isSelected) null else BorderStroke(2.dp, mainGrey)

    Button(
        modifier = modifier,
        onClick = onClick,
        contentPadding = PaddingValues(vertical = 16.dp),
        shape = MaterialTheme.shapes.medium,
        border = border,
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = container,
            contentColor = content
        )
    ) {
        Text(
            text = stringResource(id = labelId),
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Composable
fun AppIconButton(
    modifier: Modifier = Modifier,
    @DrawableRes iconId: Int,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = iconId),
            contentDescription = null,
            tint = mainGrey
        )
    }
}
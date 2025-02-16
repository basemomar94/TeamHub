package org.zayn.teamhub.core.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun BaseTopAppBar(
    modifier: Modifier = Modifier,
    text: String?,
    onNavigationClick: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        title = {
            if (text != null) Text(
                text = text,
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.body1.copy(fontSize = 18.sp)
            )
        },
        actions = actions,
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colors.secondaryVariant,
                )
            }
        },
        modifier = modifier.background(Color.Transparent),
    )
}
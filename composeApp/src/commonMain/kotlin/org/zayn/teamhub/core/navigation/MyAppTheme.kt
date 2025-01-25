package org.zayn.teamhub.core.navigation

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable


@Composable
fun MyAppTheme(content: @Composable () -> Unit) {
    val colorScheme: Colors = lightColors(
        primary = AppColors.Primary,
        secondary = AppColors.Secondary,
        background = AppColors.Background,
        surface = AppColors.Surface,
        onPrimary = AppColors.OnPrimary,
        onSecondary = AppColors.OnSecondary,
        onBackground = AppColors.OnBackground,
        onSurface = AppColors.OnSurface
    )

    MaterialTheme(
        colors = colorScheme,
        content = content
    )
}


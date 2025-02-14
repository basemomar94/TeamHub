package org.zayn.teamhub.core.desgin_repo

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun Hspacer(width: Dp) {
    Spacer(modifier = Modifier.width(width))
}

@Composable
fun Vspacer(height: Dp) {
    Spacer(modifier = Modifier.height(height))
}
package org.zayn.teamhub.core.desgin_repo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BaseSnackBar(snackBarState: SnackbarHostState, modifier: Modifier = Modifier) {
    SnackbarHost(
        modifier = modifier,
        hostState = snackBarState
    ) { snackBarData ->
        Snackbar(
            action = {
                Text(
                    text = "Dismiss",
                    style = MaterialTheme.typography.button,
                    color = MaterialTheme.colors.secondaryVariant,
                    modifier = Modifier
                        .clickable { snackBarData.dismiss() }
                        .padding(8.dp)
                )
            }
        ) {
            Text(text = snackBarData.message, style = MaterialTheme.typography.button)
        }
    }
}

package org.zayn.teamhub.core.desgin_repo

import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.stringResource
import teamhub.composeapp.generated.resources.Res
import teamhub.composeapp.generated.resources.cancel
import teamhub.composeapp.generated.resources.confirm

@Composable
fun ConfirmationDialog(
    onDismiss: () -> Unit,
    title: String,
    subTitle: String,
    confirmText: String = stringResource(Res.string.confirm),
    cancelText: String = stringResource(Res.string.cancel),
    onConfirmClick: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onConfirmClick()
            }) {
                Text(confirmText)
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismiss()
            }) {
                Text(cancelText)
            }
        },
        title = { Text(text = title, style = MaterialTheme.typography.h6) },
        text = { Text(text = subTitle, style = MaterialTheme.typography.body2) }
    )

}
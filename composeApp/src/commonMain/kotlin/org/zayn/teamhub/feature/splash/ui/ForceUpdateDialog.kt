package org.zayn.teamhub.feature.splash.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.zayn.teamhub.core.utils.getAppVersion
import org.zayn.teamhub.core.utils.openUrl
import teamhub.composeapp.generated.resources.Res
import teamhub.composeapp.generated.resources.update_message
import teamhub.composeapp.generated.resources.update_now
import teamhub.composeapp.generated.resources.update_required_title

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForceUpdateDialog() {
    val updateTitle = stringResource(Res.string.update_required_title)
    val updateMessage = stringResource(Res.string.update_message, getAppVersion())
    val updateNowText = stringResource(Res.string.update_now)

    AlertDialog(
        onDismissRequest = {}, // Prevent dismissing
        title = { Text(text = updateTitle, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold) },
        text = { Text(text = updateMessage, style = MaterialTheme.typography.bodyMedium) },
        confirmButton = {
            Button(
                onClick = { openUrl("https://appdistribution.firebase.dev/i/f3736c7c005d2a0d") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(updateNowText)
            }
        },
        modifier = Modifier.padding(16.dp)
    )
}


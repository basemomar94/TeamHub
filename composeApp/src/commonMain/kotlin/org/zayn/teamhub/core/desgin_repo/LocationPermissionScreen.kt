package org.zayn.teamhub.core.desgin_repo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LocationPermissionScreen(
    permissionGranted: Boolean,
    onRequestPermission: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (permissionGranted) "Location Permission Granted" else "Permission Required",
            style = MaterialTheme.typography.body2
        )
        Vspacer(16.dp)

        if (!permissionGranted) {
            Button(onClick = onRequestPermission) {
                Text("Request Location Permission")
            }
        }
    }
}

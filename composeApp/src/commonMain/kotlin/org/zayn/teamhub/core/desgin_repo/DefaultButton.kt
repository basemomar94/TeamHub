package org.zayn.teamhub.core.desgin_repo

import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun DefaultButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    OutlinedButton(onClick = { onClick() }, modifier = modifier, content = {
        Text(text, style = MaterialTheme.typography.body1)
    })
}
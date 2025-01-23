package org.zayn.teamhub.core.desgin_repo

import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun DefaultButton(text: String, onClick: () -> Unit) {
    OutlinedButton(onClick = { onClick() }, content = {
        Text(text)
    })
}


@Preview
@Composable
fun DefaultButtonPreview() {
    DefaultButton("Log In") {

    }
}
package org.zayn.teamhub.core.desgin_repo

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun MobileEditTextPreview() {
    var password by remember { mutableStateOf("") }
    MailEditText("") { password = it }
}

@Composable
fun MailEditText(mail: String, onMailChange: (String) -> Unit) {
    CustomTextField(
        placeHolder = "Enter Mail",
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
        query = mail,
        endIcon = Icons.Default.Clear,
        startIcon = Icons.Default.Email,
        endIconAction = { onMailChange("") }
    ) {
        onMailChange(it)
    }
}
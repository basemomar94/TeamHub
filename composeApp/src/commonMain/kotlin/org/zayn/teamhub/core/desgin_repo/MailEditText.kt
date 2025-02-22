package org.zayn.teamhub.core.desgin_repo

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import org.jetbrains.compose.resources.stringResource
import teamhub.composeapp.generated.resources.Res
import teamhub.composeapp.generated.resources.enter_mail


@Composable
fun MailEditText(mail: String, onMailChange: (String) -> Unit) {
    CustomTextField(
        placeHolder = stringResource(Res.string.enter_mail),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
        query = mail,
        endIcon = Icons.Default.Clear,
        startIcon = Icons.Default.Email,
        endIconAction = { onMailChange("") }
    ) {
        onMailChange(it)
    }
}
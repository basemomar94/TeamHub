package org.zayn.teamhub.core.desgin_repo

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import org.jetbrains.compose.resources.stringResource
import teamhub.composeapp.generated.resources.Res
import teamhub.composeapp.generated.resources.enter_mail


@Composable
fun PhoneEditText(phone: String?, onPhoneChange: (String) -> Unit) {
    CustomTextField(
        placeHolder = "Enter your phone number",
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
        query = phone.orEmpty(),
        endIcon = Icons.Default.Clear,
        startIcon = Icons.Default.Phone,
        endIconAction = { onPhoneChange("") }
    ) {
        onPhoneChange(it)
    }
}
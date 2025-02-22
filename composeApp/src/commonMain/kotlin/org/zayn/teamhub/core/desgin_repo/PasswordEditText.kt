package org.zayn.teamhub.core.desgin_repo

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import org.jetbrains.compose.resources.stringResource
import teamhub.composeapp.generated.resources.Res
import teamhub.composeapp.generated.resources.enter_password

@Composable
fun PasswordEditText(password: String, onPasswordChange: (String) -> Unit) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    CustomTextField(
        placeHolder = stringResource(Res.string.enter_password),
        startIcon = Icons.Default.Person,
        endIcon = if (isPasswordVisible) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowUp,
        endIconAction = { isPasswordVisible = !isPasswordVisible },
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        query = password
    ) {
        onPasswordChange(it)
    }
}
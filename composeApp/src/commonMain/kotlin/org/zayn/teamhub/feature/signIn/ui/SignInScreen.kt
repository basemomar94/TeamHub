package org.zayn.teamhub.feature.signIn.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.zayn.teamhub.core.desgin_repo.CustomTextField
import org.zayn.teamhub.core.desgin_repo.DefaultButton
import org.zayn.teamhub.core.desgin_repo.LoadingIndicator
import org.zayn.teamhub.feature.signIn.SignInEvent
import org.zayn.teamhub.feature.signIn.SignInState
import org.zayn.teamhub.feature.signIn.SignInViewModel

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = koinInject()
) {
    val state by viewModel.viewState.collectAsState()

    when (state) {
        SignInState.Idle -> {}
        SignInState.Loading -> {
            LoadingIndicator()
        }

        is SignInState.SignInFailure ->{}
        SignInState.SignInSuccess -> {}
    }
    SignIn { mail, password ->
        viewModel.setEvent(SignInEvent.SingIn(email = mail, password = password))

    }
}

@Composable
fun SignIn(onSigInClick: (String, String) -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column(modifier = Modifier.padding(22.dp)) {
        CustomTextField(placeHolder = "Enter Mail", query = email) {
            email = it
        }
        Spacer(modifier = Modifier.height(8.dp))
        CustomTextField(placeHolder = "Enter Password", query = password) {
            password = it
        }
        Spacer(modifier = Modifier.height(8.dp))
        DefaultButton("Login") {
            onSigInClick(email, password)
        }

    }

}


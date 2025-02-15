package org.zayn.teamhub.feature.signIn.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.zayn.teamhub.core.base.SideEffectsKey
import org.zayn.teamhub.core.desgin_repo.CustomTextField
import org.zayn.teamhub.core.desgin_repo.DefaultButton
import org.zayn.teamhub.core.desgin_repo.LoadingIndicator
import org.zayn.teamhub.core.utils.Logger
import org.zayn.teamhub.core.utils.Logger.Companion.createLogger
import org.zayn.teamhub.feature.signIn.SignInEvent
import org.zayn.teamhub.feature.signIn.SignInSideEffect
import org.zayn.teamhub.feature.signIn.SignInState
import org.zayn.teamhub.feature.signIn.SignInViewModel

@Composable
fun SignInScreen(
    onSignIn: () -> Unit,
    onSignUp: () -> Unit,
    viewModel: SignInViewModel = koinInject(),
) {
    val state by viewModel.viewState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(SideEffectsKey) {
        viewModel.effect.onEach { effect ->
            when (effect) {
                is SignInSideEffect.ShowSnackBar -> {
                    Logger.createLogger("").d("effect is ${effect.message}")
                    coroutineScope.launch {
                        snackBarHostState.showSnackbar(effect.message)
                    }

                }

                SignInSideEffect.Navigate -> onSignIn()
            }
        }.collectLatest { }
    }



    Box(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        when (state) {
            SignInState.Idle -> {
                SignIn(onSignUpClick = onSignUp, onSigInClick = { mail, password ->
                    viewModel.setEvent(SignInEvent.SingIn(email = mail, password = password))
                })
            }

            SignInState.Loading -> {
                LoadingIndicator()
            }
        }
        SnackbarHost(
            modifier = Modifier.align(Alignment.BottomCenter),
            hostState = snackBarHostState
        )

    }
}

@Composable
fun SignIn(onSigInClick: (String, String) -> Unit, onSignUpClick: () -> Unit) {
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
        DefaultButton("Login", modifier = Modifier.fillMaxWidth()) {
            onSigInClick(email, password)
        }
        Text(style = MaterialTheme.typography.body1,
            text = "Create a new account",
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable { onSignUpClick() })

    }

}


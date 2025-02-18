package org.zayn.teamhub.feature.sign_up

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.zayn.teamhub.core.base.SideEffectsKey
import org.zayn.teamhub.core.desgin_repo.BaseSnackBar
import org.zayn.teamhub.core.desgin_repo.LoadingIndicator
import org.zayn.teamhub.core.utils.Logger
import org.zayn.teamhub.core.utils.Logger.Companion.createLogger

@Composable
fun SignupScreen(viewModel: SignupViewModel = koinInject(), navigateHome: () -> Unit) {
    val log = Logger.createLogger("SignupScreen")

    val state by viewModel.viewState.collectAsState()
    log.d("state is $state")
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(SideEffectsKey) {
        viewModel.effect.onEach { effect ->
            log.d("effect is $effect")
            when (effect) {
                SignupSideEffect.NavigateHome -> navigateHome()
                is SignupSideEffect.ShowMessage -> {
                    coroutineScope.launch {
                        snackBarHostState.showSnackbar(effect.message)
                    }
                }
            }
        }.collectLatest { }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        when (state) {
            SignupState.UnInitialized -> {
                SignupCompose {
                    viewModel.setEvent(SignupEvent.AddSignup(it))
                }
            }

            SignupState.Loading -> LoadingIndicator()
        }


        BaseSnackBar(snackBarHostState, modifier = Modifier.align(Alignment.BottomCenter))
    }

}
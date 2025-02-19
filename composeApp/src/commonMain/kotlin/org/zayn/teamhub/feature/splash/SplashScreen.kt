package org.zayn.teamhub.feature.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import org.koin.compose.viewmodel.koinViewModel
import org.zayn.teamhub.core.base.SideEffectsKey
import org.zayn.teamhub.core.desgin_repo.LoadingIndicator

@Composable
fun SplashScreen(viewModel: SplashViewModel = koinViewModel(), navigateTo: () -> Unit) {

    val state by viewModel.viewState.collectAsState()
    LaunchedEffect(SideEffectsKey) {
        viewModel.effect.onEach { effect ->
            when (effect) {
                SplashEffect.Navigate -> navigateTo()
                is SplashEffect.ShowSnackBar -> TODO()
            }
        }.collectLatest { }
    }

    when (state) {
        SplashState.Ideal -> viewModel.setEvent(SplashEvent.GetSessionData)
        SplashState.Loading -> LoadingIndicator()
    }


}
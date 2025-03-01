package org.zayn.teamhub.feature.splash.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.koin.compose.viewmodel.koinViewModel
import org.zayn.teamhub.core.desgin_repo.HandleSideEffects
import org.zayn.teamhub.core.desgin_repo.LoadingIndicator
import org.zayn.teamhub.core.utils.Logger
import org.zayn.teamhub.core.utils.Logger.Companion.createLogger
import org.zayn.teamhub.feature.splash.SplashEffect
import org.zayn.teamhub.feature.splash.SplashEvent
import org.zayn.teamhub.feature.splash.SplashState
import org.zayn.teamhub.feature.splash.SplashViewModel

@Composable
fun SplashScreen(viewModel: SplashViewModel = koinViewModel(), navigateTo: () -> Unit) {
    val state by viewModel.viewState.collectAsState()
    var isForceUpdate by remember { mutableStateOf(false) }
    val logger = Logger.createLogger("SplashScreen")

    HandleSideEffects(viewModel.effect) {
        when (it) {
            SplashEffect.ForceUpdate -> isForceUpdate = true
            SplashEffect.Navigate -> navigateTo()
            is SplashEffect.ShowSnackBar -> TODO()
        }
        logger.d("effect is $it")
    }

    when (state) {
        SplashState.Ideal -> viewModel.setEvent(SplashEvent.GetSessionData)
        SplashState.Loading -> LoadingIndicator()
    }

    if (isForceUpdate) {
        ForceUpdateDialog()
    }


}
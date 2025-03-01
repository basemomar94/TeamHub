package org.zayn.teamhub.feature.splash

import org.zayn.teamhub.core.base.ViewEvent
import org.zayn.teamhub.core.base.ViewSideEffect
import org.zayn.teamhub.core.base.ViewState

sealed class SplashState : ViewState {
    data object Ideal : SplashState()
    data object Loading : SplashState()
}

sealed class SplashEvent : ViewEvent {
    data object GetSessionData : SplashEvent()

}

sealed class SplashEffect : ViewSideEffect {
    data object Navigate : SplashEffect()
    data class ShowSnackBar(val message: String) : SplashEffect()
    data object ForceUpdate : SplashEffect()
}
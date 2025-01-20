package org.zayn.teamhub.feature.signIn

import org.zayn.teamhub.core.base.ViewEvent
import org.zayn.teamhub.core.base.ViewSideEffect
import org.zayn.teamhub.core.base.ViewState

sealed class SignInEvent : ViewEvent {
    data class SingIn(val email: String, val password: String) : SignInEvent()
}

sealed class SignInState : ViewState {
    data object Loading : SignInState()
    data object Idle : SignInState()
    data object SignInSuccess : SignInState()
    data class SignInFailure(val message: String) : SignInState()
}


sealed class SignInSideEffect : ViewSideEffect {
    data class ShowError(val message: String) : SignInSideEffect()
}
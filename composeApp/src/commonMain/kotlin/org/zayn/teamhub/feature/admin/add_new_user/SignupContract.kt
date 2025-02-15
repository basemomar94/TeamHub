package org.zayn.teamhub.feature.admin.add_new_user

import org.zayn.teamhub.core.base.ViewEvent
import org.zayn.teamhub.core.base.ViewSideEffect
import org.zayn.teamhub.core.base.ViewState
import org.zayn.teamhub.core.models.User

sealed class SignupState : ViewState {
    data object UnInitialized : SignupState()
    data object Loading : SignupState()

}

sealed class SignupEvent : ViewEvent {
    data class AddSignup(val user: User) : SignupEvent()

}

sealed class SignupSideEffect : ViewSideEffect {
    data object NavigateHome : SignupSideEffect()

}
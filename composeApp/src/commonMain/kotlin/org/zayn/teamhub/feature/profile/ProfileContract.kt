package org.zayn.teamhub.feature.profile

import org.zayn.teamhub.core.base.ViewEvent
import org.zayn.teamhub.core.base.ViewSideEffect
import org.zayn.teamhub.core.base.ViewState

sealed class ProfileEvent : ViewEvent {
    data object OnLogoutClicked : ProfileEvent()
}

sealed class ProfileState : ViewState {
    data object Ideal : ProfileState()
    data object Logout : ProfileState()
}

sealed class ProfileEffect : ViewSideEffect {

}
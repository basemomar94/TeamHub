package org.zayn.teamhub

import org.zayn.teamhub.core.base.ViewEvent
import org.zayn.teamhub.core.base.ViewSideEffect
import org.zayn.teamhub.core.base.ViewState
import org.zayn.teamhub.core.models.User

sealed class MainState : ViewState {
    data object Ideal : MainState()
    data object Loading : MainState()
    data class SessionData(val user: User) : MainState()

}

sealed class MainEvent : ViewEvent {
    data object GetSessionData : MainEvent()

}

sealed class MainEffect : ViewSideEffect {

}
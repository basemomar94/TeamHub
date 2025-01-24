package org.zayn.teamhub.feature.admin.add_new_user

import org.zayn.teamhub.core.base.ViewEvent
import org.zayn.teamhub.core.base.ViewSideEffect
import org.zayn.teamhub.core.base.ViewState

sealed class NewUserState : ViewState {

}

sealed class NewUserEvent : ViewEvent {

}

sealed class NewUserSideEffect : ViewSideEffect {

}
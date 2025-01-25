package org.zayn.teamhub.feature.admin.add_new_user

import org.zayn.teamhub.core.base.ViewEvent
import org.zayn.teamhub.core.base.ViewSideEffect
import org.zayn.teamhub.core.base.ViewState
import org.zayn.teamhub.core.models.User

sealed class NewUserState : ViewState {
    data object UnIntialized : NewUserState()
    data object NewUserAdded : NewUserState()

}

sealed class NewUserEvent : ViewEvent {
    data class AddNewUser(val user: User) : NewUserEvent()

}

sealed class NewUserSideEffect : ViewSideEffect {

}
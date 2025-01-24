package org.zayn.teamhub.feature.admin.usersList

import org.zayn.teamhub.core.base.ViewEvent
import org.zayn.teamhub.core.base.ViewSideEffect
import org.zayn.teamhub.core.base.ViewState
import org.zayn.teamhub.core.models.User

sealed class UserListState : ViewState {
    data object UnIntiialized : UserListState()
    data object Loading : UserListState()
    data class UsersListData(val users: List<User>?) : UserListState()

}

sealed class UserListEvent : ViewEvent {
    data object GetUsers : UserListEvent()

}

sealed class UserListSideEffect : ViewSideEffect {

}
package org.zayn.teamhub.feature.admin.usersList.presentation

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.koin.compose.koinInject
import org.zayn.teamhub.core.models.User
import org.zayn.teamhub.feature.admin.usersList.UserListEvent
import org.zayn.teamhub.feature.admin.usersList.UserListState

@Composable
fun UsersListScreen(viewModel: UserListViewModel = koinInject()) {
    val state by viewModel.viewState.collectAsState()
    var users by remember { mutableStateOf<List<User>?>(emptyList()) }
    when (state) {
        UserListState.Loading -> {}
        UserListState.UnIntiialized -> viewModel.setEvent(UserListEvent.GetUsers)
        is UserListState.UsersListData -> users = (state as UserListState.UsersListData).users
    }
    users?.let { UsersListCompose(it) }


}

@Composable
fun UsersListCompose(usersList: List<User>) {
    LazyColumn() {
        items(usersList) { user ->
            UserItem(user = user, {})

        }

    }
}
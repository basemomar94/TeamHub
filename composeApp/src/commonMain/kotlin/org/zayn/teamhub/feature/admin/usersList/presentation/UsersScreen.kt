package org.zayn.teamhub.feature.admin.usersList.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.koin.compose.koinInject
import org.zayn.teamhub.core.desgin_repo.LoadingIndicator
import org.zayn.teamhub.core.models.User
import org.zayn.teamhub.feature.admin.usersList.UserListEvent
import org.zayn.teamhub.feature.admin.usersList.UserListState

@Composable
fun UsersListScreen(viewModel: UserListViewModel = koinInject(), onUserClick: (String?) -> Unit) {
    val state by viewModel.viewState.collectAsState()
    when (state) {
        UserListState.Loading -> LoadingIndicator()
        UserListState.UnIntiialized -> viewModel.setEvent(UserListEvent.GetUsers)
        is UserListState.UsersListData -> {
            val users = (state as UserListState.UsersListData).users
            if (users != null) {
                UsersLazyCompose(usersList = users, onUserClick = onUserClick)
            }
        }
    }
}

@Composable
fun UsersLazyCompose(usersList: List<User>, onUserClick: (String?) -> Unit) {
    LazyColumn() {
        items(usersList) { user ->
            UserItem(user = user) {
                onUserClick(user.id)

            }

        }

    }
}
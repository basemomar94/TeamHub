package org.zayn.teamhub.feature.admin.usersList.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import org.zayn.teamhub.core.desgin_repo.LoadingIndicator
import org.zayn.teamhub.core.models.User
import org.zayn.teamhub.core.utils.Logger
import org.zayn.teamhub.core.utils.Logger.Companion.createLogger
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
    LazyColumn(contentPadding = PaddingValues(8.dp)) {
        itemsIndexed(usersList) { index, user ->
            UserItem(user = user, onUserClick = { onUserClick(it)
            Logger.createLogger("UsersLazyCompose").d("clicked id is $it")
            })
            if (index < usersList.lastIndex) {
                Divider(modifier = Modifier.padding(4.dp))
            }
        }

    }
}
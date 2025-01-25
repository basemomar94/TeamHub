package org.zayn.teamhub.feature.admin.usersList.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import org.zayn.teamhub.core.models.User
import org.zayn.teamhub.feature.admin.usersList.UserListEvent
import org.zayn.teamhub.feature.admin.usersList.UserListState

@Composable
fun UsersListScreen(viewModel: UserListViewModel = koinInject(), onAddClick: () -> Unit) {
    val state by viewModel.viewState.collectAsState()
    var users by remember { mutableStateOf<List<User>?>(emptyList()) }
    when (state) {
        UserListState.Loading -> {}
        UserListState.UnIntiialized -> viewModel.setEvent(UserListEvent.GetUsers)
        is UserListState.UsersListData -> users = (state as UserListState.UsersListData).users
    }
    users?.let { UsersListCompose(it) { onAddClick() } }


}

@Composable
fun UsersListCompose(usersList: List<User>, onFabClick: () -> Unit) {
    Box() {
        UsersLazyCompose(usersList)
        FloatingActionButton(
            onClick = { onFabClick() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp) // Padding for spacing from edges
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add User"
            )
        }
    }
}

@Composable
fun UsersLazyCompose(usersList: List<User>) {
    LazyColumn() {
        items(usersList) { user ->
            UserItem(user = user, {})

        }

    }
}
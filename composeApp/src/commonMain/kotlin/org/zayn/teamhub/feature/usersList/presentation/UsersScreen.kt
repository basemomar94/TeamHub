package org.zayn.teamhub.feature.usersList.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import org.zayn.teamhub.core.desgin_repo.LoadingIndicator
import org.zayn.teamhub.core.models.User
import org.zayn.teamhub.core.utils.Logger
import org.zayn.teamhub.core.utils.Logger.Companion.createLogger
import org.zayn.teamhub.feature.user_details.UserDetailsDialog
import org.zayn.teamhub.feature.user_details.UserDetailsSheet
import org.zayn.teamhub.feature.usersList.UserListEvent
import org.zayn.teamhub.feature.usersList.UserListState

@Composable
fun UsersListScreen(
    viewModel: UserListViewModel = koinInject(),
    onAttendanceClick: (String?) -> Unit,
    onEditInfoClick: (User) -> Unit
) {
    val state by viewModel.viewState.collectAsState()
    var clickedUser by remember<MutableState<User?>> { mutableStateOf(null) }
    when (state) {
        UserListState.Loading -> LoadingIndicator()
        UserListState.Ideal -> viewModel.setEvent(UserListEvent.GetUsers)
        is UserListState.UsersListData -> {
            val users = (state as UserListState.UsersListData).users
            if (users != null) {
                UsersLazyCompose(
                    usersList = users,
                    onAttendanceClick = onAttendanceClick,
                    onUserClick = {
                        clickedUser = it
                    }
                )
            }
        }
    }
    clickedUser?.let {
        UserDetailsDialog(
            user = it,
            onAttendanceClick = onAttendanceClick,
            onEditInfoClick = onEditInfoClick
        ) {
            clickedUser = null
        }
    }
}

@Composable
fun UsersLazyCompose(
    usersList: List<User>,
    onAttendanceClick: (String?) -> Unit,
    onUserClick: (User) -> Unit
) {
    LazyColumn(contentPadding = PaddingValues(8.dp)) {
        itemsIndexed(usersList) { index, user ->
            UserItem(user = user, onAttendanceClick = {
                onAttendanceClick(it)
                Logger.createLogger("UsersLazyCompose").d("clicked id is $it")
            }, onUserClick = onUserClick)
            if (index < usersList.lastIndex) {
                Divider(modifier = Modifier.padding(4.dp))
            }
        }

    }
}
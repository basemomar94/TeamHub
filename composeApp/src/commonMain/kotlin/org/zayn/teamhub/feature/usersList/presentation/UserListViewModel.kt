package org.zayn.teamhub.feature.usersList.presentation

import org.zayn.teamhub.core.base.BaseViewModel
import org.zayn.teamhub.core.usecases.GetAllCompanyUsers
import org.zayn.teamhub.core.utils.networkresultwrapper.NetworkResult
import org.zayn.teamhub.feature.usersList.UserListEvent
import org.zayn.teamhub.feature.usersList.UserListSideEffect
import org.zayn.teamhub.feature.usersList.UserListState

class UserListViewModel(private val getAllCompanyUsers: GetAllCompanyUsers) :
    BaseViewModel<UserListState, UserListEvent, UserListSideEffect>() {

    override fun setInitialState(): UserListState {
        return UserListState.UnIntiialized
    }

    override suspend fun handleEvents(event: UserListEvent) {
        when (event) {
            UserListEvent.GetUsers -> getAllCompanyUsers()
        }
    }

    private suspend fun getAllCompanyUsers() {
        launchAndCollectResult(
            flow = getAllCompanyUsers("1"),
            onStart = { setState { UserListState.Loading } },
            resultSuccess = { setState { UserListState.UsersListData((it as NetworkResult.Success).data) } },
        )
    }
}
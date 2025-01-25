package org.zayn.teamhub.feature.admin.add_new_user

import org.zayn.teamhub.core.base.BaseViewModel
import org.zayn.teamhub.core.models.User
import org.zayn.teamhub.core.usecases.AddNewUserUseCase
import org.zayn.teamhub.core.usecases.AuthNewUserUseCase
import org.zayn.teamhub.core.utils.networkresultwrapper.NetworkResult

class AddUserViewModel(
    private val newUserUseCase: AddNewUserUseCase,
    private val authNewUserUseCase: AuthNewUserUseCase
) :
    BaseViewModel<NewUserState, NewUserEvent, NewUserSideEffect>() {
    override fun setInitialState(): NewUserState {
        return NewUserState.UnIntialized
    }

    override suspend fun handleEvents(event: NewUserEvent) {
        when (event) {
            is NewUserEvent.AddNewUser -> createAuthAndUser(event.user)
        }
    }

    private suspend fun createAuthAndUser(user: User) {
        launchAndCollectResult(
            flow = authNewUserUseCase(user),
            tag = "create auth account",
            resultSuccess = { uid ->
                if (uid is NetworkResult.Success) {
                    addNewUserToDatabase(user.copy(id = uid.data))
                }
            },

            )
    }

    private suspend fun addNewUserToDatabase(user: User) {
        launchAndCollectResult(
            flow = newUserUseCase(user), // Add the user to your database
            tag = "add new user",
            resultSuccess = {
                setState { NewUserState.NewUserAdded }
            },

            )
    }
}
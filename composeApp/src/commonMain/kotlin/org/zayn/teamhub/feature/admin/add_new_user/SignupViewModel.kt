package org.zayn.teamhub.feature.admin.add_new_user

import org.zayn.teamhub.core.base.BaseViewModel
import org.zayn.teamhub.core.models.User
import org.zayn.teamhub.core.usecases.AddNewUserUseCase
import org.zayn.teamhub.core.usecases.AuthNewUserUseCase
import org.zayn.teamhub.core.utils.networkresultwrapper.NetworkResult
import org.zayn.teamhub.feature.home.HomeState

class SignupViewModel(
    private val newUserUseCase: AddNewUserUseCase,
    private val authNewUserUseCase: AuthNewUserUseCase,
) :
    BaseViewModel<SignupState, SignupEvent, SignupSideEffect>() {
    override fun setInitialState(): SignupState {
        return SignupState.UnInitialized
    }

    override suspend fun handleEvents(event: SignupEvent) {
        when (event) {
            is SignupEvent.AddSignup -> createAuthAndUser(event.user)
        }
    }

    private suspend fun createAuthAndUser(user: User) {
        launchAndCollectResult(
            flow = authNewUserUseCase(user),
            tag = "create auth account",
            onStart = { setState { SignupState.Loading } },
            resultSuccess = { uid ->
                if (uid is NetworkResult.Success) {
                    addNewUserToDatabase(user.copy(id = uid.data))
                }
            },
            resultFailure = {
                setEffect { SignupSideEffect.ShowMessage(it.error) }
            },
            onComplete = { setState { SignupState.UnInitialized } }

        )
    }

    private suspend fun addNewUserToDatabase(user: User) {
        launchAndCollectResult(
            flow = newUserUseCase(user),
            onStart = { setState { SignupState.Loading } },
            tag = "add new user",
            resultSuccess = {
                setEffect { SignupSideEffect.NavigateHome }
            },
            resultFailure = {
                setEffect { SignupSideEffect.ShowMessage(it.error) }
            }

        )
    }
}
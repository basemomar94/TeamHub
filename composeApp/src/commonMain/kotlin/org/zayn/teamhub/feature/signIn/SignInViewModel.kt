package org.zayn.teamhub.feature.signIn

import org.zayn.teamhub.core.base.BaseViewModel
import org.zayn.teamhub.core.usecases.LogInUseCase

class SignInViewModel(
    private val logInUseCase: LogInUseCase,
) :
    BaseViewModel<SignInState, SignInEvent, SignInSideEffect>() {

    override fun setInitialState(): SignInState {
        return SignInState.Idle
    }

    private suspend fun sigIn(email: String, password: String) {
        launchAndCollectResult(
            flow = logInUseCase(email = email, password = password),
            onStart = { setState { SignInState.Loading } },
            onComplete = { setState { SignInState.Idle } },
            tag = "sigIn",
            resultSuccess = {
                setEffect { SignInSideEffect.Navigate }
            },
            resultFailure = {
                setEffect {
                    SignInSideEffect.ShowSnackBar(it.error)
                }
            }
        )

    }

    override suspend fun handleEvents(event: SignInEvent) {
        when (event) {
            is SignInEvent.SingIn -> {
                sigIn(email = event.email, password = event.password)
            }
        }
    }
}
package org.zayn.teamhub.feature.splash

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.zayn.teamhub.core.base.BaseViewModel
import org.zayn.teamhub.core.models.User
import org.zayn.teamhub.core.usecases.GetCurrentUserUseCase
import org.zayn.teamhub.core.utils.data_store.ISessionManager
import org.zayn.teamhub.core.utils.networkresultwrapper.NetworkResult

class SplashViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val sessionManager: ISessionManager,
) : BaseViewModel<SplashState, SplashEvent, SplashEffect>() {
    override fun setInitialState(): SplashState {
        return SplashState.Ideal
    }

    override suspend fun handleEvents(event: SplashEvent) {
        when (event) {
            SplashEvent.GetSessionData -> getCurrentUser()
        }
    }

    private fun getCurrentUser() = viewModelScope.launch {
        launchAndCollectResult(
            onStart = { setState { SplashState.Loading } },
            onComplete = { setEffect { SplashEffect.Navigate } },
            flow = getCurrentUserUseCase(),
            tag = "getCurrentUser",
            resultSuccess = { result ->
                (result as NetworkResult.Success).data?.let { saveSessionData(user = it) }
            })
    }

    private fun saveSessionData(user: User) {
        sessionManager.putUser(user)
    }
}
package org.zayn.teamhub.feature.splash

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.zayn.teamhub.core.base.BaseViewModel
import org.zayn.teamhub.core.models.User
import org.zayn.teamhub.core.usecases.GetCompanyByIdUseCase
import org.zayn.teamhub.core.usecases.GetCurrentUserUseCase
import org.zayn.teamhub.core.utils.data_store.ISessionManager
import org.zayn.teamhub.core.utils.networkresultwrapper.NetworkResult

class SplashViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getCompanyByIdUseCase: GetCompanyByIdUseCase,
    private val sessionManager: ISessionManager,
) : BaseViewModel<SplashState, SplashEvent, SplashEffect>() {
    override fun setInitialState(): SplashState {
        return SplashState.Ideal
    }

    override suspend fun handleEvents(event: SplashEvent) {
        when (event) {
            SplashEvent.GetSessionData -> getCurrentUserAndCompany()
        }
    }

    private fun getCurrentUserAndCompany() = viewModelScope.launch {
        launchAndCollectResult(
            onStart = { setState { SplashState.Loading } },
            flow = combine(getCurrentUserUseCase(), getCompanyByIdUseCase("1")) { user, company ->
                Pair(user, company)

            },
            tag = "getCurrentUser",
            resultSuccess = { result ->
                val userResult = result.first
                val companyResult = result.second
                if (companyResult is NetworkResult.Success && userResult is NetworkResult.Success) {
                    userResult.data?.let {
                        sessionManager.putUser(it)
                    }
                    companyResult.data?.let {sessionManager.putCompany(it) }
                }

            },
            onComplete = { setEffect { SplashEffect.Navigate } },
        )
    }
}
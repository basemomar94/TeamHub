package org.zayn.teamhub.feature.splash

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.zayn.teamhub.core.base.BaseViewModel
import org.zayn.teamhub.core.usecases.GetAppSettingsUseCase
import org.zayn.teamhub.core.usecases.GetCompanyByIdUseCase
import org.zayn.teamhub.core.usecases.GetCurrentUserUseCase
import org.zayn.teamhub.core.utils.Logger.Companion.createLogger
import org.zayn.teamhub.core.utils.data_store.ISessionManager
import org.zayn.teamhub.core.utils.isVersionSupported
import org.zayn.teamhub.core.utils.networkresultwrapper.NetworkResult

class SplashViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getCompanyByIdUseCase: GetCompanyByIdUseCase,
    private val sessionManager: ISessionManager,
    private val getAppSettingsUseCase: GetAppSettingsUseCase,
) : BaseViewModel<SplashState, SplashEvent, SplashEffect>() {
    override fun setInitialState(): SplashState {
        return SplashState.Ideal
    }

    private val log = this.createLogger()
    private var isForceUpdate = false

    override suspend fun handleEvents(event: SplashEvent) {
        when (event) {
            SplashEvent.GetSessionData -> getCurrentUserAndCompany()
        }
    }

    private fun getCurrentUserAndCompany() = viewModelScope.launch {
        launchAndCollectResult(
            onStart = { setState { SplashState.Loading } },
            flow = combine(
                getCurrentUserUseCase(),
                getCompanyByIdUseCase("1"),
                getAppSettingsUseCase()
            ) { user, company, appSettings ->
                Triple(user, company, appSettings)

            },
            tag = "getCurrentUserAndCompany",
            resultSuccess = { result ->
                val userResult = result.first
                val companyResult = result.second
                val settingsResult = result.third
                if (userResult is NetworkResult.Success) {
                    userResult.data?.let {
                        log.d("saving user $it")
                        sessionManager.putUser(it)
                    }
                }
                if (companyResult is NetworkResult.Success) {
                    companyResult.data?.let {
                        sessionManager.putCompany(it)
                    }
                }
                if (settingsResult is NetworkResult.Success) {
                    val latestSupported = settingsResult.data?.minimumVersion ?: "0.0.0"
                    if (!isVersionSupported(latestSupported)) {
                        setEffect { SplashEffect.ForceUpdate }
                        isForceUpdate = true
                    }

                }
            },
            onComplete = { if (!isForceUpdate) setEffect { SplashEffect.Navigate } },
        )
    }
}
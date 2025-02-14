package org.zayn.teamhub.feature.home

import org.zayn.teamhub.core.base.BaseViewModel
import org.zayn.teamhub.core.models.AttendanceType
import org.zayn.teamhub.core.services.SessionManager
import org.zayn.teamhub.core.usecases.AddAttendanceUseCase
import org.zayn.teamhub.core.usecases.GetCurrentUserUseCase
import org.zayn.teamhub.core.utils.networkresultwrapper.NetworkResult

class HomeViewModel(
    private val sessionManager: SessionManager,
    private val userUseCase: GetCurrentUserUseCase,
    private val attendanceUseCase: AddAttendanceUseCase,
) :
    BaseViewModel<HomeState, HomeEvent, HomeSideEffect>() {
    val userId = sessionManager.getUserId()

    override fun setInitialState(): HomeState {
        return HomeState.UnInitialized
    }

    override suspend fun handleEvents(event: HomeEvent) {
        when (event) {
            HomeEvent.GetUserData -> getUser()
            is HomeEvent.AddAttendance -> addAttendance(event.type)
        }
    }

    private suspend fun addAttendance(type: AttendanceType) {
        launchAndCollectResult(flow = attendanceUseCase(type), tag = "addAttendance")
    }

    private suspend fun getUser() {
        launchAndCollectResult(
            tag = "getUser",
            flow = userUseCase(),
            onStart = { setState { HomeState.Loading } },
            resultSuccess = {
                if (it is NetworkResult.Success) {
                    setState { HomeState.UserData(it.data) }
                }
            }
        )
    }
}
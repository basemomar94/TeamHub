package org.zayn.teamhub.feature.home

import kotlinx.coroutines.flow.combine
import org.zayn.teamhub.core.base.BaseViewModel
import org.zayn.teamhub.core.models.AttendanceType
import org.zayn.teamhub.core.usecases.AddAttendanceLogUseCase
import org.zayn.teamhub.core.usecases.GetCurrentUserUseCase
import org.zayn.teamhub.core.usecases.UpdateUserAttendanceUseCase
import org.zayn.teamhub.core.utils.networkresultwrapper.NetworkResult

class HomeViewModel(
    private val userUseCase: GetCurrentUserUseCase,
    private val attendanceUseCase: AddAttendanceLogUseCase,
    private val updateUserAttendanceUseCase: UpdateUserAttendanceUseCase
) :
    BaseViewModel<HomeState, HomeEvent, HomeSideEffect>() {

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
        launchAndCollectResult(
            flow = combine(
                attendanceUseCase(type),
                updateUserAttendanceUseCase(type)
            ) { attendance, updateAttendance ->
                Pair(attendance, updateAttendance)

            }, tag = "addAttendance",
            resultSuccess = { result ->
                if (result.first is NetworkResult.Success && result.second is NetworkResult.Success) {
                    setEffect { HomeSideEffect.Success }
                }
            }
        )
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
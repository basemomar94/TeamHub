package org.zayn.teamhub.feature.home

import kotlinx.coroutines.flow.combine
import org.zayn.teamhub.core.base.BaseViewModel
import org.zayn.teamhub.core.models.AttendanceType
import org.zayn.teamhub.core.models.User
import org.zayn.teamhub.core.usecases.AddAttendanceLogUseCase
import org.zayn.teamhub.core.usecases.GetAllCompanyUsers
import org.zayn.teamhub.core.usecases.GetCurrentUserUseCase
import org.zayn.teamhub.core.usecases.UpdateUserAttendanceUseCase
import org.zayn.teamhub.core.utils.data_store.ISessionManager
import org.zayn.teamhub.core.utils.getCurrentLocation
import org.zayn.teamhub.core.utils.isGpsAvailable
import org.zayn.teamhub.core.utils.isLocationAllowed
import org.zayn.teamhub.core.utils.networkresultwrapper.NetworkResult

class HomeViewModel(
    private val userUseCase: GetCurrentUserUseCase,
    private val attendanceUseCase: AddAttendanceLogUseCase,
    private val updateUserAttendanceUseCase: UpdateUserAttendanceUseCase,
    private val getCompanyUsers: GetAllCompanyUsers,
    private val sessionManager: ISessionManager,
) :
    BaseViewModel<HomeState, HomeEvent, HomeSideEffect>() {
    private val currentUser = sessionManager.getUser()

    override fun setInitialState(): HomeState {
        return HomeState.UnInitialized
    }

    override suspend fun handleEvents(event: HomeEvent) {
        when (event) {
            HomeEvent.GetUserData -> getCurrentAndOnlineUsers()
            is HomeEvent.AddAttendance -> addAttendance(event.type)
        }
    }

    private suspend fun addAttendance(type: AttendanceType) {
        if (!isLocationAllowed()) {
            setEffect { HomeSideEffect.ShowSnackBar(RecordAttendanceError.LocationNotAllowed) }
            return
        }
        if (!isGpsAvailable()) {
            setEffect { HomeSideEffect.ShowSnackBar(RecordAttendanceError.GPSNotAllowed) }
            return
        }
        val location = getCurrentLocation()
        launchAndCollectResult(
            flow = combine(
                attendanceUseCase(
                    userId = currentUser?.id ?: "",
                    attendanceType = type,
                    lat = location?.first,
                    lon = location?.second
                ),
                updateUserAttendanceUseCase(type = type, userId = currentUser?.id ?: "")
            ) { attendance, updateAttendance ->
                Pair(attendance, updateAttendance)

            }, tag = "addAttendance",
            onStart = { setState { HomeState.Loading } },
            onComplete = { setState { HomeState.UnInitialized } },
            resultSuccess = { result ->
                if (result.first is NetworkResult.Success && result.second is NetworkResult.Success) {
                    setEffect { HomeSideEffect.ShowSnackBar(RecordAttendanceError.AttendanceRecorded) }
                }
            },
            resultFailure = {
                setEffect { HomeSideEffect.ShowSnackBar(RecordAttendanceError.ApiError(it.error)) }
            }
        )
    }

    private suspend fun getCurrentAndOnlineUsers() {
        launchAndCollectResult(
            tag = "getUser",
            flow = combine(
                getCompanyUsers(companyId = "1"),
                userUseCase()
            ) { onlineUsers, currentUser ->
                Pair(onlineUsers, currentUser)
            },
            onStart = { setState { HomeState.Loading } },
            resultSuccess = { result ->
                if (result.first is NetworkResult.Success && result.second is NetworkResult.Success) {
                    val online =
                        (result.first as NetworkResult.Success<List<User>>).data?.sortedByDescending { it.currentStatus == AttendanceType.CLOCK_IN.name }
                    val user = (result.second as NetworkResult.Success<User>).data
                    sessionManager.putUserId(user?.id ?: "")
                    setState {
                        HomeState.HomeData(
                            onlineUsers = online,
                            currentUser = user
                        )
                    }

                }
            },
            resultFailure = {
                setEffect { HomeSideEffect.ShowSnackBar(RecordAttendanceError.ApiError(it.error)) }
            }
        )
    }
}
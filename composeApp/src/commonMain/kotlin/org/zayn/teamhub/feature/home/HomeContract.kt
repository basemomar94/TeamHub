package org.zayn.teamhub.feature.home

import org.zayn.teamhub.core.base.ViewEvent
import org.zayn.teamhub.core.base.ViewSideEffect
import org.zayn.teamhub.core.base.ViewState
import org.zayn.teamhub.core.models.Attendance
import org.zayn.teamhub.core.models.AttendanceType
import org.zayn.teamhub.core.models.User

sealed class HomeState : ViewState {
    data object UnInitialized : HomeState()
    data object Loading : HomeState()
    data class UserData(val user: User?) : HomeState()
    data object AttendanceUpdate : HomeState()
}

sealed class HomeEvent() : ViewEvent {
    data object GetUserData : HomeEvent()
    data class AddAttendance(val type: AttendanceType) : HomeEvent()

}

sealed class HomeSideEffect() : ViewSideEffect {
    data class Error(val reason: String) : HomeSideEffect()
    data object Success : HomeSideEffect()

}
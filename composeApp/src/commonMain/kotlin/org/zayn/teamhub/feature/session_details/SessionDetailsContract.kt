package org.zayn.teamhub.feature.session_details

import org.zayn.teamhub.core.base.ViewEvent
import org.zayn.teamhub.core.base.ViewSideEffect
import org.zayn.teamhub.core.base.ViewState
import org.zayn.teamhub.core.models.Attendance

sealed class SessionDetailsState : ViewState {
    data object Ideal : SessionDetailsState()
    data object Loading : SessionDetailsState()
    data class LoadData(val attendance: Attendance) : SessionDetailsState()
}

sealed class SessionDetailsEvent : ViewEvent {
    data class GetAttendance(val id: String) : SessionDetailsEvent()
    data class UpdateAttendance(val time: Long, val id: String) : SessionDetailsEvent()
}

sealed class SessionDetailsEffect : ViewSideEffect {
    data object Navigate : SessionDetailsEffect()
    data class ShowSnackBar(val message: String) : SessionDetailsEffect()
}
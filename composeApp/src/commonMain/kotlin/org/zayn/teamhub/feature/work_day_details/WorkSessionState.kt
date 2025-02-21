package org.zayn.teamhub.feature.work_day_details

import org.zayn.teamhub.core.base.ViewEvent
import org.zayn.teamhub.core.base.ViewSideEffect
import org.zayn.teamhub.core.base.ViewState
import org.zayn.teamhub.core.models.WorkSession
import org.zayn.teamhub.core.models.WorkSession2
import org.zayn.teamhub.feature.home.RecordAttendanceError

sealed class WorkSessionEvent : ViewEvent {
    data class GetSessionDetails(val userId: String, val startOfDay: Long, val endOfDay: Long) :
        WorkSessionEvent()

    data class EndSession(val userId: String) : WorkSessionEvent()
}

sealed class WorkSessionState : ViewState {
    data object Ideal : WorkSessionState()
    data object Loading : WorkSessionState()
    data class SessionDetails(val attendanceList: List<WorkSession2>) : WorkSessionState()

}

sealed class WorkSessionEffect : ViewSideEffect {
    data class ShowMessage(val error: RecordAttendanceError) : WorkSessionEffect()
}
package org.zayn.teamhub.feature.work_day_details

import org.zayn.teamhub.core.base.ViewEvent
import org.zayn.teamhub.core.base.ViewState
import org.zayn.teamhub.core.models.Attendance

sealed class WorkDayEvent : ViewEvent {
    data class GetDayDetails(val day: Long) : WorkDayEvent()
}

sealed class WorkDayState : ViewState {
    data object Ideal : WorkDayState()
    data object Loading : WorkDayState()
    data class DayDetails(val attendanceList: List<Attendance>) : WorkDayState()

}

sealed class WorkDayEffect : ViewState {
    data class ShowMessage(val message: String) : WorkDayEffect()
}
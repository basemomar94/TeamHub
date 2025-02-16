package org.zayn.teamhub.feature.work_day_details

import org.zayn.teamhub.core.base.ViewEvent
import org.zayn.teamhub.core.base.ViewSideEffect
import org.zayn.teamhub.core.base.ViewState
import org.zayn.teamhub.core.models.WorkSession

sealed class WorkDayEvent : ViewEvent {
    data class GetDayDetails(val userId: String) : WorkDayEvent()
}

sealed class WorkDayState : ViewState {
    data object Ideal : WorkDayState()
    data object Loading : WorkDayState()
    data class DayDetails(val attendanceList: List<WorkSession>) : WorkDayState()

}

sealed class WorkDayEffect : ViewSideEffect {
    data class ShowMessage(val message: String) : WorkDayEffect()
}
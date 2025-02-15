package org.zayn.teamhub.feature.work_summary

import org.zayn.teamhub.core.base.ViewEvent
import org.zayn.teamhub.core.base.ViewSideEffect
import org.zayn.teamhub.core.base.ViewState
import org.zayn.teamhub.core.models.WorkDaySummary

sealed class WorkSummaryEvent : ViewEvent {
    data class LoadUserAttendance(val userId: String) : WorkSummaryEvent()
}

sealed class WorkSummaryState : ViewState {
    data object UnInitialized : WorkSummaryState()
    data object Loading : WorkSummaryState()
    data class Success(val attendanceList: List<WorkDaySummary>?) : WorkSummaryState()
    data class Error(val message: String) : WorkSummaryState()
}

sealed class WorkSummaryEffect : ViewSideEffect {
    data class ShowMessage(val message: String) : WorkSummaryEffect()
    data object NavigateBack : WorkSummaryEffect()
}

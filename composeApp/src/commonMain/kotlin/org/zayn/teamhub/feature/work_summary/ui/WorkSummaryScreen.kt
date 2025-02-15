package org.zayn.teamhub.feature.work_summary.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.compose.viewmodel.koinViewModel
import org.zayn.teamhub.core.desgin_repo.LoadingIndicator
import org.zayn.teamhub.feature.work_summary.WorkSummaryEvent
import org.zayn.teamhub.feature.work_summary.WorkSummaryState
import org.zayn.teamhub.feature.work_summary.WorkSummaryViewModel

@Composable
fun WorkDaySummaryScreen(userId: String, viewModel: WorkSummaryViewModel = koinViewModel()) {
    val state by viewModel.viewState.collectAsState()
    when (state) {
        is WorkSummaryState.Error -> TODO()
        WorkSummaryState.Loading -> LoadingIndicator()
        is WorkSummaryState.Success -> {
            (state as WorkSummaryState.Success).attendanceList?.let { AttendanceList(it) }
        }

        WorkSummaryState.UnInitialized -> {
            viewModel.setEvent(WorkSummaryEvent.LoadUserAttendance(userId))
        }
    }

}
package org.zayn.teamhub.feature.work_day_details.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.compose.viewmodel.koinViewModel
import org.zayn.teamhub.core.desgin_repo.LoadingIndicator
import org.zayn.teamhub.feature.work_day_details.WorkDayEvent
import org.zayn.teamhub.feature.work_day_details.WorkDayState
import org.zayn.teamhub.feature.work_day_details.WorkSessionViewModel

@Composable
fun WorkDayScreen(viewModel: WorkSessionViewModel = koinViewModel(), userId: String) {
    val state by viewModel.viewState.collectAsState()
    when (state) {
        WorkDayState.Ideal -> viewModel.setEvent(WorkDayEvent.GetDayDetails(userId))
        WorkDayState.Loading -> LoadingIndicator()
        is WorkDayState.DayDetails -> {
            WorkSessionList((state as WorkDayState.DayDetails).attendanceList)
        }
    }

}
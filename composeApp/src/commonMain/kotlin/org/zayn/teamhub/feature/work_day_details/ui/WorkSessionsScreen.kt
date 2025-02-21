package org.zayn.teamhub.feature.work_day_details.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.compose.viewmodel.koinViewModel
import org.zayn.teamhub.core.desgin_repo.LoadingIndicator
import org.zayn.teamhub.feature.work_day_details.WorkSessionEvent
import org.zayn.teamhub.feature.work_day_details.WorkSessionState
import org.zayn.teamhub.feature.work_day_details.WorkSessionViewModel

@Composable
fun WorkDayScreen(
    viewModel: WorkSessionViewModel = koinViewModel(),
    userId: String,
    startOfDay: Long,
    endOfDay: Long,
) {
    val state by viewModel.viewState.collectAsState()
    val isAdmin = viewModel.isAdmin
    when (state) {
        WorkSessionState.Ideal -> viewModel.setEvent(
            WorkSessionEvent.GetSessionDetails(
                userId = userId,
                startOfDay = startOfDay,
                endOfDay = endOfDay
            )
        )

        WorkSessionState.Loading -> LoadingIndicator()
        is WorkSessionState.SessionDetails -> {
            val sessionsList = (state as WorkSessionState.SessionDetails).attendanceList
            WorkSessionList(sessionsList, isAdmin) {
                viewModel.setEvent(WorkSessionEvent.EndSession(it))

            }
        }
    }

}
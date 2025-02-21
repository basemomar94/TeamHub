package org.zayn.teamhub.feature.session_details.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.compose.viewmodel.koinViewModel
import org.zayn.teamhub.core.desgin_repo.LoadingIndicator
import org.zayn.teamhub.feature.session_details.SessionDetailsEvent
import org.zayn.teamhub.feature.session_details.SessionDetailsState
import org.zayn.teamhub.feature.session_details.SessionDetailsViewModel

@Composable
fun SessionDetailsScreen(
    attendanceId: String?,
    viewModel: SessionDetailsViewModel = koinViewModel()
) {

    val state by viewModel.viewState.collectAsState()

    when (state) {
        SessionDetailsState.Ideal -> viewModel.setEvent(
            SessionDetailsEvent.GetAttendance(
                attendanceId ?: ""
            )
        )

        is SessionDetailsState.LoadData -> SessionDetailsCompose((state as SessionDetailsState.LoadData).attendance)
        SessionDetailsState.Loading -> LoadingIndicator()
    }

}
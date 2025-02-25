package org.zayn.teamhub.feature.session_details.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel
import org.zayn.teamhub.core.desgin_repo.BaseSnackBar
import org.zayn.teamhub.core.desgin_repo.HandleSideEffects
import org.zayn.teamhub.core.desgin_repo.LoadingIndicator
import org.zayn.teamhub.feature.session_details.SessionDetailsEffect
import org.zayn.teamhub.feature.session_details.SessionDetailsEvent
import org.zayn.teamhub.feature.session_details.SessionDetailsState
import org.zayn.teamhub.feature.session_details.SessionDetailsViewModel

@Composable
fun SessionDetailsScreen(
    attendanceId: String?,
    onUpdateSuccess: () -> Unit,
    viewModel: SessionDetailsViewModel = koinViewModel(),
) {

    val state by viewModel.viewState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }

    HandleSideEffects(effectFlow = viewModel.effect) {
        when (it) {
            SessionDetailsEffect.Navigate -> {
                onUpdateSuccess()
            }

            is SessionDetailsEffect.ShowSnackBar -> snackBarHostState.showSnackbar(it.message)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (state) {
            SessionDetailsState.Ideal -> viewModel.setEvent(
                SessionDetailsEvent.GetAttendance(
                    attendanceId ?: ""
                )
            )

            is SessionDetailsState.LoadData -> SessionDetailsCompose(
                attendance = (state as SessionDetailsState.LoadData).attendance,
                onNewTimeSelected = {
                    viewModel.setEvent(
                        SessionDetailsEvent.UpdateAttendance(
                            time = it,
                            id = attendanceId ?: ""
                        )
                    )
                })

            SessionDetailsState.Loading -> LoadingIndicator()
        }
        BaseSnackBar(
            snackBarState = snackBarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }

}
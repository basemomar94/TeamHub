package org.zayn.teamhub.feature.work_summary.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import org.zayn.teamhub.core.base.SideEffectsKey
import org.zayn.teamhub.core.desgin_repo.BaseSnackBar
import org.zayn.teamhub.core.desgin_repo.LoadingIndicator
import org.zayn.teamhub.core.models.WorkDaySummary
import org.zayn.teamhub.feature.work_summary.WorkSummaryEffect
import org.zayn.teamhub.feature.work_summary.WorkSummaryEvent
import org.zayn.teamhub.feature.work_summary.WorkSummaryState
import org.zayn.teamhub.feature.work_summary.WorkSummaryViewModel

@Composable
fun WorkDaySummaryScreen(
    userId: String,
    onDayClick: (WorkDaySummary) -> Unit,
    viewModel: WorkSummaryViewModel = koinViewModel(),
) {
    val state by viewModel.viewState.collectAsState()

    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(SideEffectsKey) {
        viewModel.effect.onEach { effect ->
            when (effect) {
                is WorkSummaryEffect.ShowMessage -> {
                    coroutineScope.launch {
                        snackBarHostState.showSnackbar(effect.message)
                    }

                }

                WorkSummaryEffect.NavigateBack -> {}
            }
        }.collectLatest { }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (state) {
            WorkSummaryState.Loading -> LoadingIndicator()
            is WorkSummaryState.Success -> {

                (state as WorkSummaryState.Success).attendanceList?.let {
                    Column {
                        AttendanceSummary(
                            totalDays = it.size,
                            totalHours = (it.sumOf { it.totalMinutesWorked } / 60).toInt()
                        )
                        AttendanceList(workdayList = it, onWorkDayClick = onDayClick)
                    }

                }
            }

            WorkSummaryState.UnInitialized -> {
                viewModel.setEvent(WorkSummaryEvent.LoadUserAttendance(userId))
            }
        }
        BaseSnackBar(snackBarHostState, Modifier.align(Alignment.BottomCenter))

    }


}
package org.zayn.teamhub.feature.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.zayn.teamhub.core.base.SideEffectsKey
import org.zayn.teamhub.core.desgin_repo.BaseSnackBar
import org.zayn.teamhub.core.desgin_repo.CustomAlertMessage
import org.zayn.teamhub.core.desgin_repo.LoadingIndicator
import org.zayn.teamhub.core.desgin_repo.Vspacer
import org.zayn.teamhub.core.models.AttendanceType
import org.zayn.teamhub.core.models.User
import org.zayn.teamhub.core.utils.toLocalizedDateTime
import org.zayn.teamhub.feature.home.HomeEvent
import org.zayn.teamhub.feature.home.RecordAttendanceError
import org.zayn.teamhub.feature.home.HomeSideEffect
import org.zayn.teamhub.feature.home.HomeState
import org.zayn.teamhub.feature.home.HomeViewModel
import teamhub.composeapp.generated.resources.Res
import teamhub.composeapp.generated.resources.attendance_recorded
import teamhub.composeapp.generated.resources.clock_in
import teamhub.composeapp.generated.resources.clock_out
import teamhub.composeapp.generated.resources.gps_not_allowed
import teamhub.composeapp.generated.resources.location_permission_required

@Composable
fun HomeScreen(viewModel: HomeViewModel = koinInject(), onAttendanceClick: (String?) -> Unit) {
    val state by viewModel.viewState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var showDeveloperDialog by remember { mutableStateOf(false) }
    val attendanceRecordedText = stringResource(Res.string.attendance_recorded)
    val gpsNotAllowedText = stringResource(Res.string.gps_not_allowed)
    val locationPermissionNotGranted = stringResource(Res.string.location_permission_required)

    LaunchedEffect(SideEffectsKey) {
        viewModel.effect.onEach { effect ->
            when (effect) {
                is HomeSideEffect.ShowSnackBar -> {
                    coroutineScope.launch {
                        when (effect.message) {
                            is RecordAttendanceError.ApiError -> snackBarHostState.showSnackbar(
                                effect.message.message
                            )

                            RecordAttendanceError.AttendanceRecorded -> snackBarHostState.showSnackbar(
                                attendanceRecordedText
                            )

                            RecordAttendanceError.GPSNotAllowed -> {
                                snackBarHostState.showSnackbar(
                                    gpsNotAllowedText
                                )
                            }

                            RecordAttendanceError.LocationNotAllowed -> {
                                snackBarHostState.showSnackbar(
                                    locationPermissionNotGranted
                                )
                            }
                        }
                    }
                }
            }
        }.collectLatest { }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        when (state) {
            HomeState.Loading -> LoadingIndicator()
            HomeState.UnInitialized -> viewModel.setEvent(HomeEvent.GetUserData)
            is HomeState.HomeData -> {
                val onlineUsers = (state as HomeState.HomeData).onlineUsers
                val currentUser = (state as HomeState.HomeData).currentUser
                if (onlineUsers != null && currentUser != null) {
                    HomeCompose(
                        onlineUsers = onlineUsers,
                        user = currentUser,
                        onAttendanceClick = {onAttendanceClick(it)}
                    ) { type ->

                        if (true) {
                            viewModel.setEvent(HomeEvent.AddAttendance(type))
                        } else {
                            showDeveloperDialog = true
                        }
                    }
                }

            }
        }
        BaseSnackBar(
            snackBarState = snackBarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
    if (showDeveloperDialog) {
        CustomAlertMessage(title = "", message = "") {
            showDeveloperDialog = !showDeveloperDialog
        }
    }

}

@Composable
fun HomeCompose(
    user: User,
    onlineUsers: List<User>,
    onAttendanceClick: (String?) -> Unit,
    addAttendance: (AttendanceType) -> Unit
) {
    Column {
        WelcomeHeader(user.firstName ?: "")
        Vspacer(8.dp)
        Column(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            AttendanceButton(
                title = stringResource(Res.string.clock_in),
                attendanceTime = if (user.currentStatus == AttendanceType.CLOCK_IN.name) user.lastUpdate.toLocalizedDateTime() else "-- --",
                isEnabled = user.currentStatus == AttendanceType.CLOCK_OUT.name || user.currentStatus == null
            ) {
                addAttendance(AttendanceType.CLOCK_IN)
            }
            Vspacer(16.dp)

            AttendanceButton(
                title = stringResource(Res.string.clock_out),
                attendanceTime = if (user.currentStatus == AttendanceType.CLOCK_OUT.name) user.lastUpdate.toLocalizedDateTime() else "-- --",
                isEnabled = user.currentStatus == AttendanceType.CLOCK_IN.name

            ) {
                addAttendance(AttendanceType.CLOCK_OUT)
            }

        }
        Vspacer(8.dp)
        OnlineUserGrid(onlineUsers) {
         onAttendanceClick(it)
        }

    }

}

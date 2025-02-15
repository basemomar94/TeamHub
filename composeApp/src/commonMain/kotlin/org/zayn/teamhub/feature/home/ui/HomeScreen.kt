package org.zayn.teamhub.feature.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import org.koin.compose.koinInject
import org.zayn.teamhub.core.base.SideEffectsKey
import org.zayn.teamhub.core.desgin_repo.CustomAlertMessage
import org.zayn.teamhub.core.desgin_repo.LoadingIndicator
import org.zayn.teamhub.core.desgin_repo.Vspacer
import org.zayn.teamhub.core.models.AttendanceType
import org.zayn.teamhub.core.models.User
import org.zayn.teamhub.core.utils.toLocalizedDateTime
import org.zayn.teamhub.feature.home.HomeEvent
import org.zayn.teamhub.feature.home.HomeSideEffect
import org.zayn.teamhub.feature.home.HomeState
import org.zayn.teamhub.feature.home.HomeViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = koinInject()) {
    val state by viewModel.viewState.collectAsState()
    var isLoading by remember<MutableState<Boolean>> { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }
    var dialogTitle by remember { mutableStateOf("") }

    LaunchedEffect(SideEffectsKey) {
        viewModel.effect.onEach { effect ->
            when (effect) {
                is HomeSideEffect.Error -> {
                    dialogTitle = "Error"
                    dialogMessage = effect.reason
                    showDialog = true
                }

                HomeSideEffect.Success -> {
                    viewModel.setEvent(HomeEvent.GetUserData)
                    dialogTitle = "Success"
                    dialogMessage = "Operation completed successfully!"
                    showDialog = true
                }
            }
        }.collectLatest { }
    }
    when (state) {
        HomeState.Loading -> LoadingIndicator()
        HomeState.UnInitialized -> viewModel.setEvent(HomeEvent.GetUserData)
        is HomeState.UserData -> {
            (state as HomeState.UserData).user?.let {
                HomeCompose(it) {
                    viewModel.setEvent(HomeEvent.AddAttendance(it))
                }
            }
        }

        HomeState.AttendanceUpdate -> {}
    }
    if (showDialog) {
        CustomAlertMessage(title = dialogTitle, message = dialogMessage) {
            showDialog = false

        }
    }

}

@Composable
fun HomeCompose(user: User, addAttendance: (AttendanceType) -> Unit) {
    Column {
        WelcomeHeader(user.firstName ?: "") {

        }

        Column(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            AttendanceButton(
                title = "Clock In",
                attendanceTime = if (user.currentStatus == AttendanceType.CLOCK_IN.name) user.lastUpdate.toLocalizedDateTime() else "-- --",
                isEnabled = user.currentStatus == AttendanceType.CLOCK_OUT.name || user.currentStatus == null
            ) {
                addAttendance(AttendanceType.CLOCK_IN)
            }
            Vspacer(16.dp)

            AttendanceButton(
                title = "Clock Out",
                attendanceTime = if (user.currentStatus == AttendanceType.CLOCK_OUT.name) user.lastUpdate.toLocalizedDateTime() else "-- --",
                isEnabled = user.currentStatus == AttendanceType.CLOCK_IN.name

            ) {
                addAttendance(AttendanceType.CLOCK_OUT)
            }

        }
    }

}

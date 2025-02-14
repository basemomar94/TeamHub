package org.zayn.teamhub.feature.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import org.zayn.teamhub.core.models.AttendanceType
import org.zayn.teamhub.core.models.User
import org.zayn.teamhub.core.utils.toLocalizedDateTime
import org.zayn.teamhub.feature.home.HomeEvent
import org.zayn.teamhub.feature.home.HomeState
import org.zayn.teamhub.feature.home.HomeViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = koinInject()) {
    val state by viewModel.viewState.collectAsState()
    var isLoading by remember<MutableState<Boolean>> { mutableStateOf(false) }
    var user by remember<MutableState<User?>> { mutableStateOf(null) }
    when (state) {
        HomeState.Loading -> isLoading = true
        HomeState.UnInitialized -> viewModel.setEvent(HomeEvent.GetUserData)
        is HomeState.UserData -> {
            (state as HomeState.UserData).user?.let {
                HomeCompose(it) {
                    viewModel.setEvent(HomeEvent.AddAttendance(it))
                }
            }
        }
    }

}

@Composable
fun HomeCompose(user: User, addAttendance: (AttendanceType) -> Unit) {
    Column {
        WelcomeHeader(user.firstName ?: "") {

        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AttendanceButton(
                title = "Check In",
                attendanceTime = if (user.currentStatus == AttendanceType.CLOCK_IN.name) user.lastUpdate.toLocalizedDateTime() else "-- --",
                isEnabled = user.currentStatus == AttendanceType.CLOCK_OUT.name
            ) {
                addAttendance(AttendanceType.CLOCK_IN)
            }
            AttendanceButton(
                title = "Check Out",
                attendanceTime = if (user.currentStatus == AttendanceType.CLOCK_IN.name) user.lastUpdate.toLocalizedDateTime() else "-- --",
                isEnabled = user.currentStatus == AttendanceType.CLOCK_IN.name

            ) {
                addAttendance(AttendanceType.CLOCK_OUT)
            }

        }
    }

}

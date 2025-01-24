package org.zayn.teamhub.feature.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        AttendanceButton(
            title = "Check In",
            attendanceTime = "11:30",
            isEnabled = false,
            modifier = Modifier
        ) {
        }
        AttendanceButton(
            title = "Check Out",
            attendanceTime = "--:--",
            isEnabled = true,
            modifier = Modifier
        ) {
        }
    }
}

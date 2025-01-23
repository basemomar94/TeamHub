package org.zayn.teamhub.feature.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun AttendanceButton(
    title: String,
    attendanceTime: String,
    isEnabled: Boolean,
    onClick: () -> Unit,
) {

    Button(onClick = onClick) {
        Column {
            Text(text = title, style = MaterialTheme.typography.h3)
            Text(text = title, style = MaterialTheme.typography.h2)

        }
    }
}

@Preview
@Composable
fun AttendanceButtonPreview() {
    AttendanceButton(title = "Check In", attendanceTime = "25/3/25 03:25 am", isEnabled = true) {}
}
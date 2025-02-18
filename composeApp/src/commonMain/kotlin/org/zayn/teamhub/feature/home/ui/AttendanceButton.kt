package org.zayn.teamhub.feature.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.zayn.teamhub.core.desgin_repo.Vspacer


@Composable
fun AttendanceButton(
    title: String,
    attendanceTime: String,
    isEnabled: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {

    OutlinedButton(
        modifier = modifier.fillMaxWidth(),
        enabled = isEnabled,
        onClick = {
            onClick()
        }, colors = ButtonDefaults.buttonColors(
            backgroundColor = if (isEnabled) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface.copy(
                alpha = 0.12f
            ),
            contentColor = if (isEnabled) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface,
        )
    ) {
        Column {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = attendanceTime,
                style = MaterialTheme.typography.button,
                textAlign = TextAlign.Center
            )
            Vspacer(4.dp)
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = title,
                style = MaterialTheme.typography.button,
                textAlign = TextAlign.Center,
            )

        }
    }
}

@Preview
@Composable
fun AttendanceButtonPreview() {
    AttendanceButton(title = "Check In", attendanceTime = "25/3/25 03:25 am", isEnabled = true) {}
}
package org.zayn.teamhub.feature.session_details.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.zayn.teamhub.core.models.Attendance
import org.zayn.teamhub.core.utils.toLocalizedDateTime

@Composable
fun SessionDetailsCompose(attendance: Attendance) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Attendance Details",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        DetailRow("ID", attendance.id)
        DetailRow("User ID", attendance.userId)
        DetailRow("Created At", attendance.createdAt.toLocalizedDateTime())
        DetailRow("Type", attendance.type)
        DetailRow("Method", attendance.method)

        if (attendance.lat != null && attendance.long != null) {
            DetailRow("Location", "${attendance.lat}, ${attendance.long}")
        }

        if (!attendance.mobileId.isNullOrEmpty()) {
            DetailRow("Mobile ID", attendance.mobileId)
        }

        if (!attendance.deviceName.isNullOrEmpty()) {
            DetailRow("Device Name", attendance.deviceName)
        }

        if (!attendance.flag.isNullOrEmpty()) {
            DetailRow("Flags", attendance.flag.joinToString(", "))
        }
    }
}
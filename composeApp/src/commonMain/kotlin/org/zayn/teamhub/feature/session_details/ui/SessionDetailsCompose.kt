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
import org.jetbrains.compose.resources.stringResource
import org.zayn.teamhub.core.models.Attendance
import org.zayn.teamhub.core.models.AttendanceFlag
import org.zayn.teamhub.core.models.Location
import org.zayn.teamhub.core.utils.openMap
import org.zayn.teamhub.core.utils.toLocalizedDateTime
import teamhub.composeapp.generated.resources.Res
import teamhub.composeapp.generated.resources.created_at
import teamhub.composeapp.generated.resources.device_name
import teamhub.composeapp.generated.resources.flags
import teamhub.composeapp.generated.resources.id
import teamhub.composeapp.generated.resources.location
import teamhub.composeapp.generated.resources.method
import teamhub.composeapp.generated.resources.type
import teamhub.composeapp.generated.resources.user_id

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

        DetailRow(stringResource(Res.string.id), attendance.id)
        DetailRow(stringResource(Res.string.user_id), attendance.userId)
        DetailRow(
            label = stringResource(Res.string.created_at),
            value = attendance.createdAt.toLocalizedDateTime(),
            isFlagged = attendance.flag?.contains(AttendanceFlag.LATE.name) == true
        )
        DetailRow(stringResource(Res.string.type), attendance.type)
        DetailRow(stringResource(Res.string.method), attendance.method)

        if (attendance.lat != null && attendance.long != null) {
            DetailRow(
                label = stringResource(Res.string.location),
                value = "${attendance.lat}, ${attendance.long}",
                isFlagged = attendance.flag?.contains(AttendanceFlag.OUT_OF_LOCATION.name) == true
            ) {
                openMap(Location(lat = attendance.lat, lon = attendance.long))
            }
        }

        if (!attendance.deviceName.isNullOrEmpty()) {
            DetailRow(
                label = stringResource(Res.string.device_name),
                value = attendance.deviceName,
                isFlagged = attendance.flag?.contains(AttendanceFlag.UNAUTHORIZED_DEVICE.name) == true
            )
        }
    }
}
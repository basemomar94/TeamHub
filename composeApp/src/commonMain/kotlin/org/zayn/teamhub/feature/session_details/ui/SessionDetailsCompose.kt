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
        DetailRow(stringResource(Res.string.created_at), attendance.createdAt.toLocalizedDateTime())
        DetailRow(stringResource(Res.string.type), attendance.type)
        DetailRow(stringResource(Res.string.method), attendance.method)

        if (attendance.lat != null && attendance.long != null) {
            DetailRow(stringResource(Res.string.location), "${attendance.lat}, ${attendance.long}")
        }

        if (!attendance.deviceName.isNullOrEmpty()) {
            DetailRow(stringResource(Res.string.device_name), attendance.deviceName)
        }

        if (!attendance.flag.isNullOrEmpty()) {
            DetailRow(stringResource(Res.string.flags), attendance.flag.joinToString(", "))
        }
    }
}
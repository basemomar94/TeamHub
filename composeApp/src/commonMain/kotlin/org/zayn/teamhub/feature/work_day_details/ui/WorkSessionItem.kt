package org.zayn.teamhub.feature.work_day_details.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.zayn.teamhub.core.models.WorkSession
import org.zayn.teamhub.core.utils.Logger
import org.zayn.teamhub.core.utils.Logger.Companion.createLogger
import org.zayn.teamhub.core.utils.openMap
import org.zayn.teamhub.core.utils.toLocalizedTime
import org.zayn.teamhub.core.utils.toWorkDuration
import teamhub.composeapp.generated.resources.Res
import teamhub.composeapp.generated.resources.clock_in
import teamhub.composeapp.generated.resources.clock_out
import teamhub.composeapp.generated.resources.hr
import teamhub.composeapp.generated.resources.mins
import teamhub.composeapp.generated.resources.no_clock_in
import teamhub.composeapp.generated.resources.session_is_on_going
import teamhub.composeapp.generated.resources.still_clocked_in
import teamhub.composeapp.generated.resources.total_time

@Composable
fun WorkSessionItem(session: WorkSession, onEndSessionClick: () -> Unit) {
    val logger = Logger.createLogger("WorkSessionItem")
    val isSessionOnGoing = session.clockOut?.createdAt?.toLocalizedTime() == null
    val clockInText =
        session.clockIn?.createdAt?.toLocalizedTime() ?: stringResource(Res.string.no_clock_in)
    val clockOutText =
        session.clockOut?.createdAt?.toLocalizedTime()
            ?: stringResource(Res.string.still_clocked_in)

    val totalMinutesWorked = if (session.clockOut?.createdAt != null && session.clockIn?.createdAt != null) {
        (session.clockOut.createdAt - session.clockIn.createdAt) / 60000
    } else {
        null
    }

    val totalTime = totalMinutesWorked?.toWorkDuration()
    val totalTimeText =
        if (totalTime != null) "${totalTime.hours} ${stringResource(Res.string.hr)} ${totalTime.minutes} ${
            stringResource(Res.string.mins)
        }" else stringResource(Res.string.session_is_on_going)


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            SessionItem(
                isOnGoing = false,
                label = stringResource(Res.string.clock_in),
                text = clockInText,
                onMapClick = {
                    openMap(session.clockIn?.location)
                },
                onClearAttendanceClick = {}
            )
            SessionItem(
                label = stringResource(Res.string.clock_out),
                text = clockOutText,
                isOnGoing = isSessionOnGoing,
                onMapClick = {
                    openMap(session.clockOut?.location)
                },
                onClearAttendanceClick = onEndSessionClick
            )

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(Res.string.total_time),
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF388E3C)
                )
                Text(text = totalTimeText, color = Color(0xFF388E3C))
            }
        }
    }
}



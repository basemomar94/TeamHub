package org.zayn.teamhub.feature.work_day_details.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.zayn.teamhub.core.desgin_repo.ConfirmationDialog
import org.zayn.teamhub.core.models.WorkSession
import org.zayn.teamhub.core.utils.Logger
import org.zayn.teamhub.core.utils.Logger.Companion.createLogger
import org.zayn.teamhub.core.utils.openMap
import org.zayn.teamhub.core.utils.toLocalizedTime
import org.zayn.teamhub.core.utils.toWorkDuration
import teamhub.composeapp.generated.resources.Res
import teamhub.composeapp.generated.resources.clock_in
import teamhub.composeapp.generated.resources.clock_out
import teamhub.composeapp.generated.resources.confirm_clock_out_message
import teamhub.composeapp.generated.resources.confirm_clock_out_title
import teamhub.composeapp.generated.resources.hr
import teamhub.composeapp.generated.resources.mins
import teamhub.composeapp.generated.resources.no_clock_in
import teamhub.composeapp.generated.resources.session_is_on_going
import teamhub.composeapp.generated.resources.still_clocked_in
import teamhub.composeapp.generated.resources.total_time

@Composable
fun WorkSessionItem(
    session: WorkSession,
    isAdmin: Boolean,
    onEndSessionClick: () -> Unit,
    onSessionClick: (String?) -> Unit
) {
    val logger = Logger.createLogger("WorkSessionItem")
    val isSessionOnGoing = session.clockOut?.createdAt?.toLocalizedTime() == null
    val clockInText =
        session.clockIn?.createdAt?.toLocalizedTime() ?: stringResource(Res.string.no_clock_in)
    val clockOutText =
        session.clockOut?.createdAt?.toLocalizedTime()
            ?: stringResource(Res.string.still_clocked_in)

    val totalMinutesWorked =
        if (session.clockOut?.createdAt != null && session.clockIn?.createdAt != null) {
            (session.clockOut.createdAt - session.clockIn.createdAt) / 60000
        } else {
            null
        }

    val totalTime = totalMinutesWorked?.toWorkDuration()
    val totalTimeText = stringResource(Res.string.total_time) +
            if (totalTime != null) "${totalTime.hours} ${stringResource(Res.string.hr)} ${totalTime.minutes} ${
                stringResource(Res.string.mins)
            }" else stringResource(Res.string.session_is_on_going)

    var showConfirmDialog by remember { mutableStateOf(false) }


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
                onSessionClick = { onSessionClick(session.clockIn?.id) },
                label = stringResource(Res.string.clock_in),
                text = clockInText,
                onMapClick = {
                    openMap(session.clockIn?.location)
                },
            )
            SessionItem(
                onSessionClick = { onSessionClick(session.clockOut?.id) },
                label = stringResource(Res.string.clock_out),
                text = clockOutText,
                onMapClick = {
                    openMap(session.clockOut?.location)
                },
            )

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (isSessionOnGoing) {
                    if (isAdmin) {
                        Icon(
                            Icons.Default.PlayArrow,
                            modifier = Modifier.size(24.dp),
                            contentDescription = ""
                        )
                        IconButton(
                            onClick = { showConfirmDialog = true },
                            modifier = Modifier.size(24.dp),
                        ) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = "")
                        }
                    }

                } else {
                    Text(text = totalTimeText, color = Color(0xFF388E3C))
                }

            }
        }
    }

    if (showConfirmDialog) {
        ConfirmationDialog(
            onDismiss = { showConfirmDialog = false },
            title = stringResource(Res.string.confirm_clock_out_title),
            subTitle = stringResource(Res.string.confirm_clock_out_message),
        ) {
            onEndSessionClick()

        }
    }
}



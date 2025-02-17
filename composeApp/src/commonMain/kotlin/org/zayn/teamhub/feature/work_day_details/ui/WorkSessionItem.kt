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

@Composable
fun WorkSessionItem(session: WorkSession) {
    val logger = Logger.createLogger("WorkSessionItem")
    val clockInText = session.clockInTime?.toLocalizedTime() ?: "No Clock-In"
    val clockOutText = session.clockOutTime?.toLocalizedTime() ?: "Still Clocked In"

    val totalMinutesWorked = if (session.clockOutTime != null && session.clockInTime != null) {
        (session.clockOutTime - session.clockInTime) / 60000
    } else {
        null
    }

    val totalTimeText = totalMinutesWorked?.toWorkDuration() ?: "Ongoing session"

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
            SessionItem(label = stringResource(Res.string.clock_in), text = clockInText) {
                val lat = session.clockInLocation?.lat
                val long = session.clockInLocation?.long
                if (lat != null && long != null) {
                    openMap(
                        latitude = lat,
                        longitude = long
                    )
                } else {
                    logger.e("location isn't provided")
                }

            }
            SessionItem(label = stringResource(Res.string.clock_out), text = clockOutText) {
                val lat = session.clockOutLocation?.lat
                val long = session.clockOutLocation?.long
                if (lat != null && long != null) {
                    openMap(
                        latitude = lat,
                        longitude = long
                    )
                } else {
                    logger.e("location isn't provided")
                }
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total Time:",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF388E3C)
                )
                Text(text = totalTimeText, color = Color(0xFF388E3C))
            }
        }
    }
}



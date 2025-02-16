package org.zayn.teamhub.feature.work_day_details.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.zayn.teamhub.core.models.WorkSession
import org.zayn.teamhub.core.utils.toLocalizedDateTime
import org.zayn.teamhub.core.utils.toLocalizedTime

@Composable
fun WorkSessionItem(session: WorkSession) {
    val clockInText = session.clockInTime?.toLocalizedTime() ?: "No Clock-In"
    val clockOutText = session.clockOutTime?.toLocalizedTime() ?: "Still Clocked In"

    val totalMinutesWorked = if (session.clockOutTime != null && session.clockInTime != null) {
        (session.clockOutTime - session.clockInTime) / 60000 // Convert millis to minutes
    } else {
        null
    }

    val totalTimeText = totalMinutesWorked?.let { minutes ->
        val hours = minutes / 60
        val remainingMinutes = minutes % 60
        if (hours > 0) {
            "$hours hrs $remainingMinutes min"
        } else {
            "$remainingMinutes min"
        }
    } ?: "Ongoing session"

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
            Text(
                text = "Work Session",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Clock In:", fontWeight = FontWeight.Bold)
                Text(text = clockInText)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Clock Out:", fontWeight = FontWeight.Bold)
                Text(text = clockOutText)
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Total Time:", fontWeight = FontWeight.Bold, color = Color(0xFF388E3C)) // Green color for emphasis
                Text(text = totalTimeText, color = Color(0xFF388E3C))
            }
        }
    }
}



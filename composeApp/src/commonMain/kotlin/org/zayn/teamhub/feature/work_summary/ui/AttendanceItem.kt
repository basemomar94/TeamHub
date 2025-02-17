package org.zayn.teamhub.feature.work_summary.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.zayn.teamhub.core.models.WorkDaySummary
import org.zayn.teamhub.core.utils.toWorkDuration

@Composable
fun AttendanceItem(summary: WorkDaySummary, onDayClick: (WorkDaySummary) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp).clickable { summary.userId?.let { onDayClick(summary) } },
        elevation = 4.dp,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // Date Header
            Text(
                text = summary.date,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Work Duration using the extension function
            Text(
                text = "Total Worked: ${summary.totalMinutesWorked.toWorkDuration()}",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Progress Indicator (Optional)
            LinearProgressIndicator(
                progress = (summary.totalMinutesWorked / 480f).coerceIn(
                    0f,
                    1f
                ), // Assuming 8-hour workdays (480 minutes)
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp),
                color = Color.Blue
            )
        }
    }
}

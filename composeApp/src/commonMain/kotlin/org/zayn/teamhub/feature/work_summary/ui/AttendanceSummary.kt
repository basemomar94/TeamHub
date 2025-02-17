package org.zayn.teamhub.feature.work_summary.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.zayn.teamhub.core.desgin_repo.Vspacer
import teamhub.composeapp.generated.resources.Res
import teamhub.composeapp.generated.resources.total_working_days
import teamhub.composeapp.generated.resources.total_working_hours


@Composable
fun AttendanceSummary(totalDays: Int, totalHours: Int) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(12.dp),
    ) {
        Column(modifier = Modifier.padding(8.dp).fillMaxWidth()) {
            Text(
                textAlign = TextAlign.Center,
                text = stringResource(Res.string.total_working_days, totalDays),
                style = MaterialTheme.typography.body2
            )
            Vspacer(8.dp)
            Text(
                textAlign = TextAlign.Center,
                text = stringResource(Res.string.total_working_hours, totalHours),
                style = MaterialTheme.typography.body2
            )
        }

        /* LazyVerticalGrid(
             columns = GridCells.Fixed(2),
             modifier = Modifier.fillMaxSize(),
             contentPadding = PaddingValues(16.dp)
         ) {
             SummaryItem(icon = Icons.Default.Person, title = "This month Attendance", count =)
         }*/
    }

}
package org.zayn.teamhub.feature.work_summary.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import org.zayn.teamhub.core.models.WorkDaySummary

@Composable
fun AttendanceList(workdayList: List<WorkDaySummary>, onWorkDayClick: (String) -> Unit) {
    LazyColumn {
        items(workdayList) { work ->
            AttendanceItem(work){
                onWorkDayClick(it)
            }
        }

    }
}
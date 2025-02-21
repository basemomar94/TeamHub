package org.zayn.teamhub.feature.work_day_details.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import org.zayn.teamhub.core.models.WorkSession

@Composable
fun WorkSessionList(
    workdayList: List<WorkSession>,
    isAdmin: Boolean,
    onSessionEnd: (String) -> Unit
) {
    LazyColumn {
        items(workdayList) { work ->
            WorkSessionItem(work, isAdmin) { onSessionEnd(work.userId ?: "") }
        }

    }
}
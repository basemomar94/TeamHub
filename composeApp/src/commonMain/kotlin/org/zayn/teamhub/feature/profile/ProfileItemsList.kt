package org.zayn.teamhub.feature.profile

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable

@Composable
fun ProfileItemsList(onClick: (ProfileItem) -> Unit) {
    val profileItems = listOf(attendanceItem, logoutItem)

    LazyColumn {
        items(items = profileItems) { item ->
            ProfileItemCompose(item) {
                onClick(item)
            }
        }
    }
}
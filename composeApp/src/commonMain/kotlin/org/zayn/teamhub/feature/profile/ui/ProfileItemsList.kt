package org.zayn.teamhub.feature.profile.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable

@Composable
fun ProfileItemsList(onClick: (ProfileItem) -> Unit) {
    val profileItems = listOf(attendanceItem, editProfileItem, logoutItem)

    LazyColumn {
        items(items = profileItems) { item ->
            ProfileItemCompose(item) {
                onClick(item)
            }
        }
    }
}
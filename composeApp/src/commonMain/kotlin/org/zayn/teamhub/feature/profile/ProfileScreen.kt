package org.zayn.teamhub.feature.profile

import androidx.compose.runtime.Composable

@Composable
fun ProfileScreen(onClick: (ProfileItem) -> Unit) {
    ProfileItemsList {
        onClick(it)
    }
}
package org.zayn.teamhub.feature.admin

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Button
import androidx.compose.runtime.Composable

@Composable
fun DashBoardScreen(onUsersClick: () -> Unit) {
    Box {

        Button(onClick = { onUsersClick() }, content = {

        })
    }
}
package org.zayn.teamhub.feature.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DashBoardScreen(onUsersClick: () -> Unit) {
    Box(modifier = Modifier.padding(12.dp)) {

        DashBoardCard("Users", Icons.Default.Person) {
            onUsersClick()
        }
    }
}
package org.zayn.teamhub.feature.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ProfileItemCompose(profileItem: ProfileItem, onClick: (ProfileItem) -> Unit) {
    Column(modifier =Modifier.padding(12.dp) ) {
        Row(
            modifier = Modifier.clickable { onClick(profileItem) },
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(imageVector = profileItem.icon, contentDescription = profileItem.title)
            Text(text = profileItem.title, modifier = Modifier)
            Image(
                imageVector = Icons.AutoMirrored.Default.ArrowForward,
                contentDescription = "",
                modifier = Modifier
            )

        }
        Divider()
    }


}

data class ProfileItem(
    val title: String,
    val icon: ImageVector,
    val action: ProfileAction,
)

enum class ProfileAction {
    LOG_OUT, ATTENDANCE
}

val attendanceItem = ProfileItem(
    title = "My Attendance",
    icon = Icons.Default.Person,
    action = ProfileAction.ATTENDANCE
)

val logoutItem = ProfileItem(
    title = "Log out",
    icon = Icons.Default.Lock,
    action = ProfileAction.LOG_OUT
)
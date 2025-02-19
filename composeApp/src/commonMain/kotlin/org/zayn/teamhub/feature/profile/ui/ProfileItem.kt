package org.zayn.teamhub.feature.profile.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import teamhub.composeapp.generated.resources.Res
import teamhub.composeapp.generated.resources.edit_profile
import teamhub.composeapp.generated.resources.enter_mail
import teamhub.composeapp.generated.resources.log_out
import teamhub.composeapp.generated.resources.my_attendance

@Composable
fun ProfileItemCompose(profileItem: ProfileItem, onClick: (ProfileItem) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().clickable { onClick(profileItem) }) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(imageVector = profileItem.icon, contentDescription = "")
            Text(text = stringResource(profileItem.title), modifier = Modifier.weight(1f))
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
    val title: StringResource,
    val icon: ImageVector,
    val action: ProfileAction,
)

enum class ProfileAction {
    LOG_OUT, ATTENDANCE, EDIT_PROFILE
}

val attendanceItem = ProfileItem(
    title = Res.string.my_attendance,
    icon = Icons.Default.Person,
    action = ProfileAction.ATTENDANCE
)

val logoutItem = ProfileItem(
    title = Res.string.log_out,
    icon = Icons.Default.Lock,
    action = ProfileAction.LOG_OUT
)

val editProfileItem =
    ProfileItem(
        title = Res.string.edit_profile,
        icon = Icons.Default.Edit,
        action = ProfileAction.EDIT_PROFILE
    )
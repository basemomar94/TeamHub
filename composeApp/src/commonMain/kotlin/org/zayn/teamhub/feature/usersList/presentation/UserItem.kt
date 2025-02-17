package org.zayn.teamhub.feature.usersList.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.zayn.teamhub.core.models.AttendanceType
import org.zayn.teamhub.core.models.Roles
import org.zayn.teamhub.core.models.User
import org.zayn.teamhub.core.utils.Logger
import org.zayn.teamhub.core.utils.Logger.Companion.createLogger
import org.zayn.teamhub.core.utils.toLocalizedDateTime
import teamhub.composeapp.generated.resources.Res
import teamhub.composeapp.generated.resources.last_update

@Composable
fun UserItem(
    user: User,
    onUserClick: (String) -> Unit,
) {
    val isUserOnline = user.currentStatus == AttendanceType.CLOCK_IN.name
    val indicatorColor = if (isUserOnline) Color.Green else Color.Gray
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                user.id?.let {
                    onUserClick(it)
                }


            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(indicatorColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = user.firstName?.take(1)?.uppercase().orEmpty() + user.lastName?.take(1)
                    ?.uppercase().orEmpty(),
                style = MaterialTheme.typography.body1,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "${user.firstName.orEmpty()} ${user.lastName.orEmpty()}",
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = if (user.lastUpdate != 0L)  stringResource(Res.string.last_update) + user.lastUpdate.toLocalizedDateTime() else "",
                style = MaterialTheme.typography.body2,
                color = Color.Gray
            )
        }

        Box(
            modifier = Modifier.size(12.dp).background(
                shape = CircleShape,
                color = indicatorColor
            )
        )

        if (user.role == Roles.ADMIN.name) {
            Text(
                text = "Admin",
                color = MaterialTheme.colors.secondary,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold
            )
        }
    }
}



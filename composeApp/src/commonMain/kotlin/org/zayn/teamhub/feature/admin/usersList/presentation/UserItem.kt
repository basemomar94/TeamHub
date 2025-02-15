package org.zayn.teamhub.feature.admin.usersList.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import org.zayn.teamhub.core.models.AttendanceType
import org.zayn.teamhub.core.models.User
import org.zayn.teamhub.core.utils.toLocalizedDateTime

@Composable
fun UserItem(
    user: User,
    onUserClick: (User) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onUserClick(user) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(MaterialTheme.colors.primary, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = user.firstName?.take(1).orEmpty() + user.lastName?.take(1).orEmpty(),
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
                text = "Last update: ${user.lastUpdate.toLocalizedDateTime()}",
                style = MaterialTheme.typography.body2,
                color = Color.Gray
            )
        }

        Box(
            modifier = Modifier.size(12.dp).background(
                shape = CircleShape,
                color = if (user.currentStatus == AttendanceType.CLOCK_IN.name) Color.Green else Color.Gray
            )
        )

        if (user.isAdmin == true) {
            Text(
                text = "Admin",
                color = MaterialTheme.colors.secondary,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold
            )
        }
    }
}



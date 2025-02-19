package org.zayn.teamhub.feature.usersList.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.zayn.teamhub.core.desgin_repo.Hspacer
import org.zayn.teamhub.core.models.User
import org.zayn.teamhub.core.utils.toLocalizedDateTime
import org.zayn.teamhub.feature.home.ui.UserCircularItem
import teamhub.composeapp.generated.resources.Res
import teamhub.composeapp.generated.resources.last_update

@Composable
fun UserItem(
    user: User,
    onUserClick: (String) -> Unit,
) {
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
        UserCircularItem(user)
        Hspacer(8.dp)
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "${user.firstName.orEmpty()} ${user.lastName.orEmpty()}",
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = if (user.lastUpdate != 0L) stringResource(Res.string.last_update) + user.lastUpdate.toLocalizedDateTime() else "",
                style = MaterialTheme.typography.body2,
                color = Color.Gray
            )
        }
    }
}



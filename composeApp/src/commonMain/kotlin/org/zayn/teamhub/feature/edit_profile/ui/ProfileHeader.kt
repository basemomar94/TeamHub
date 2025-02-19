package org.zayn.teamhub.feature.edit_profile.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.zayn.teamhub.core.desgin_repo.Vspacer

@Composable
fun ProfileHeader(
    modifier: Modifier = Modifier,
    userImage: String?,
    userName: String,
    onProfileClicked: () -> Unit,
) {
    Row(
        modifier = modifier
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProfileImageWithEdit(userImage = userImage) {
            onProfileClicked()
        }
        Vspacer(12.dp)
        Column {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = userName,
                style = MaterialTheme.typography.subtitle1.copy(
                    color = MaterialTheme.colors.onSurface,
                    fontWeight = FontWeight.SemiBold
                )
            )

        }
    }
}
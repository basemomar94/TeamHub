package org.zayn.teamhub.feature.edit_profile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.zayn.teamhub.core.desgin_repo.BaseImage

@Composable
fun ProfileImageWithEdit(
    userImage: String?,
    modifier: Modifier = Modifier,
    onEditClicked: () -> Unit,
) {
    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = modifier.size(100.dp)
    ) {
        BaseImage(
            url = userImage, modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
        )

        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colors.secondaryVariant)
                .border(4.dp, MaterialTheme.colors.background, CircleShape)
                .clickable { onEditClicked.invoke() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Edit,
                contentDescription = "",
                tint = Color.Black,
                modifier = Modifier.size(22.dp)
            )
        }
    }
}




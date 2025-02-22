package org.zayn.teamhub.feature.work_day_details.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Flag
import org.zayn.teamhub.core.desgin_repo.Vspacer

@Composable
fun SessionItem(
    label: String,
    text: String,
    modifier: Modifier = Modifier,
    onSessionClick: () -> Unit,
    isFlagged: Boolean,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onSessionClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = label,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.body2
            )
            Vspacer(4.dp)
            Text(
                text = text,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface
            )
        }
        if (isFlagged) {
            Icon(
                imageVector = FontAwesomeIcons.Solid.Flag,
                modifier = Modifier.padding(8.dp).size(24.dp),
                contentDescription = "Navigate",
                tint = Color.Red
            )
        }

    }
}

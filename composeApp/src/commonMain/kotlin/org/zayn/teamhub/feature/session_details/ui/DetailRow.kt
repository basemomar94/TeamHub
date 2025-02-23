package org.zayn.teamhub.feature.session_details.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun DetailRow(
    label: String,
    value: String,
    isFlagged: Boolean = false,
    onClick: (() -> Unit?)? = null
) {
    val clipboardManager = LocalClipboardManager.current

    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable { onClick?.invoke() },
        contentColor = if (isFlagged) MaterialTheme.colors.error else MaterialTheme.colors.primary

    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$label:",
                style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.width(140.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))

            Text(overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                text = value,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.weight(1f)
                    .clickable { clipboardManager.setText(AnnotatedString(value)) })
        }
    }
}

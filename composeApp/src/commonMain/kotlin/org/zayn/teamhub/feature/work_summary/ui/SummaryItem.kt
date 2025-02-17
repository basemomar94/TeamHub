package org.zayn.teamhub.feature.work_summary.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun SummaryItem(icon: ImageVector, title: String, count: Int) {
    Column(modifier = Modifier.padding(8.dp)) {
        Image(imageVector = icon, contentDescription = title)
        Text(text = title, style = MaterialTheme.typography.body1)
        Text(text = title, style = MaterialTheme.typography.body2)
    }

}
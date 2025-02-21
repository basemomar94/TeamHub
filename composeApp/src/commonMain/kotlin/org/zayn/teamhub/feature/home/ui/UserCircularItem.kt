package org.zayn.teamhub.feature.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.zayn.teamhub.core.models.AttendanceType
import org.zayn.teamhub.core.models.User

@Composable
fun UserCircularItem(user: User, modifier: Modifier = Modifier, onClick: ((User) -> Unit)? = null) {
    Column(modifier = modifier) {
        Box(
            contentAlignment = Alignment.BottomEnd,
            modifier = Modifier.clickable { onClick?.invoke(user) }
        ) {
            val initials = user.firstName?.firstOrNull()?.toString()?.uppercase().orEmpty() + " " +
                    user.lastName?.firstOrNull()?.toString()?.uppercase().orEmpty()

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = initials,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.White
                )
            }

            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(if (user.currentStatus == AttendanceType.CLOCK_IN.name) Color.Green else Color.Gray)
                    .border(1.dp, Color.White, CircleShape)
                    .align(Alignment.BottomEnd)
            )
        }
    }

}

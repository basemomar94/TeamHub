package org.zayn.teamhub.core.navigation

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val label: String, val icon: ImageVector) {
    data object Home :
        BottomNavItem("home", "Home", Icons.Default.Home)

    data object Profile : BottomNavItem(
        "profile",
        "Profile",
            Icons.Default.Home)

    data object Settings : BottomNavItem(
        "settings",
        "Settings",
        Icons.Default.Settings)
}
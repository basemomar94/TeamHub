package org.zayn.teamhub.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val label: String, val icon: ImageVector) {
    data object Home :
        BottomNavItem("home", "Home", Icons.Default.Home)

    data object Profile : BottomNavItem(
        "profile",
        "Profile",
        Icons.Default.Person
    )

    data object Dashboard : BottomNavItem(
        Screen.DashBoard.route,
        "Dashboard",
        Icons.Default.Info
    )
}
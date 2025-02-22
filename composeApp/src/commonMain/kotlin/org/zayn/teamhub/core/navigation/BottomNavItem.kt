package org.zayn.teamhub.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import teamhub.composeapp.generated.resources.Res
import teamhub.composeapp.generated.resources.dashboard
import teamhub.composeapp.generated.resources.home
import teamhub.composeapp.generated.resources.profile

sealed class BottomNavItem(val route: String, val label: StringResource, val icon: ImageVector) {
    data object Home :
        BottomNavItem("home", Res.string.home, Icons.Default.Home)

    data object Profile : BottomNavItem(
        "profile",
        Res.string.profile,
        Icons.Default.Person
    )

    data object Dashboard : BottomNavItem(
        Screen.DashBoard.route,
        Res.string.dashboard,
        Icons.Default.Info
    )
}
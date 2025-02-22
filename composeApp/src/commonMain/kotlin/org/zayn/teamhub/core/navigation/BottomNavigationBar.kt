package org.zayn.teamhub.core.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import org.jetbrains.compose.resources.stringResource
import org.zayn.teamhub.core.models.Roles
import org.zayn.teamhub.core.utils.data_store.ISessionManager
import org.zayn.teamhub.core.utils.enumValueOf

@Composable
fun BottomNavigationBar(navController: NavHostController, sessionManager: ISessionManager) {
    val adminMenus = listOf(
        BottomNavItem.Home,
        BottomNavItem.Profile,
        BottomNavItem.Dashboard
    )
    val userMenus = listOf(
        BottomNavItem.Home,
        BottomNavItem.Profile,
    )
    val bottomMenu = when (enumValueOf<Roles>(sessionManager.getUser()?.role, default = Roles.USER)) {
        Roles.ADMIN -> adminMenus
        Roles.USER -> userMenus
    }
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val noBottomBarScreens = listOf(Screen.SignIn.route, Screen.SignUp.route, Screen.Splash.route)
    if (!noBottomBarScreens.contains(currentRoute) && currentRoute != null) {
        BottomNavigation {
            bottomMenu.forEach { item ->
                BottomNavigationItem(
                    icon = { Icon(imageVector = item.icon, contentDescription = "") },
                    label = { Text(stringResource(item.label)) },
                    selected = currentRoute == item.route,
                    onClick = {
                        if (currentRoute != item.route) {
                            navController.navigate(item.route) {
                                launchSingleTop = true
                                restoreState = true
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}

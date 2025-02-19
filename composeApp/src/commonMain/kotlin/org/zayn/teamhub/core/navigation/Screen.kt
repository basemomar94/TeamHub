package org.zayn.teamhub.core.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList(),
) {
    data object SignIn : Screen("signIn")
    data object Home : Screen("home")
    data object DashBoard : Screen("Dashboard")
    data object UsersList : Screen("users_list")
    data object SignUp : Screen("sign_up")
    data object Profile : Screen("Profile")
    data object Splash:Screen("Splash")
    data object WorkSummary : Screen(
        route = "WorkSummary/{userId}",
        navArguments = listOf(navArgument("userId") {
            type = NavType.StringType
            nullable = true
        })
    ) {
        fun createRoute(userId: String?) = "WorkSummary/${userId}"

    }

    data object WorkSessions : Screen(
        route = "WorkSessions/{userId}/{startOfDay}/{endOfDay}",
        navArguments = listOf(navArgument("userId") {
            type = NavType.StringType
            nullable = true
        }, navArgument("startOfDay") {
            type = NavType.LongType
        }, navArgument("endOfDay") {
            type = NavType.LongType
        }

        )
    ) {
        fun createRoute(userId: String?, startOfDay: Long, endOfDay: Long) =
            "WorkSessions/${userId}/${startOfDay}/${endOfDay}"

    }


}
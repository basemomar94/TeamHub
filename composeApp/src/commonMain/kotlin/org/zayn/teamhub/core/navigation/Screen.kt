package org.zayn.teamhub.core.navigation

import androidx.compose.ui.text.input.KeyboardType.Companion.Uri
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import io.ktor.http.encodeURLParameter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.zayn.teamhub.core.models.User

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
    data object Splash : Screen("Splash")
    data object EditProfile :
        Screen(route = "EditProfile/{user}", navArguments = listOf(navArgument("user") {
            type = NavType.StringType
            nullable = true
        })) {
        fun createRoute(user: User?): String {
            return if (user != null) {
                val userJson =Json.encodeToString(user)
                val encodedUserJson = userJson.encodeURLParameter()
                "EditProfile/$encodedUserJson"
            } else {
                // Handle null appropriately. Either pass an empty string or adjust your route to use optional parameters.
                "EditProfile/"
            }
        }    }

    data object SessionDetails : Screen(
        route = "SessionDetails/{attendanceId}",
        navArguments = listOf(navArgument("attendanceId") {
            type = NavType.StringType
            nullable = true
        })
    ) {
        fun createRoute(attendanceId: String?) = "SessionDetails/${attendanceId}"

    }

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
package org.zayn.teamhub.core.navigation

import androidx.navigation.NamedNavArgument

sealed class Screen(
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList()
) {
    data object SignIn : Screen("signIn")
    data object Home : Screen("home")
    data object DashBoard : Screen("Dashboard")
    data object UsersList : Screen("users_list")
    data object SignUp : Screen("sign_up")


}
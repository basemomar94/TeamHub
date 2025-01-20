package org.zayn.teamhub.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.zayn.teamhub.feature.home.ui.HomeScreen
import org.zayn.teamhub.feature.signIn.ui.SignInScreen

@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    TeamHubNavigationHost(navController)

}

@Composable
fun TeamHubNavigationHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.SignIn.route) {
        composable(route = Screen.SignIn.route) {
            SignInScreen(onSignIn = { navController.navigate(Screen.Home.route) })
        }
        composable(route = Screen.Home.route) {
            HomeScreen()
        }
    }

}

package org.zayn.teamhub.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.gitlive.firebase.auth.FirebaseAuth
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import org.zayn.teamhub.feature.home.ui.HomeScreen
import org.zayn.teamhub.feature.signIn.ui.SignInScreen

@Composable
@Preview
fun App(auth: FirebaseAuth = koinInject()) {
    val navController = rememberNavController()
    val userId = auth.currentUser?.uid
    val isAuthenticated = remember { userId != null }
    TeamHubNavigationHost(navController, isAuthenticated)

}

@Composable
fun TeamHubNavigationHost(navController: NavHostController, isAuthenticated: Boolean) {
    NavHost(
        navController = navController,
        startDestination = if (isAuthenticated) Screen.Home.route else Screen.SignIn.route
    ) {
        composable(route = Screen.SignIn.route) {
            SignInScreen(onSignIn = { navController.navigate(Screen.Home.route) })
        }
        composable(route = Screen.Home.route) {
            HomeScreen()
        }
    }

}

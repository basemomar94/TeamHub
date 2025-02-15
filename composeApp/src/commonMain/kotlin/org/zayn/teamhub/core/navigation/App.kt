package org.zayn.teamhub.core.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.gitlive.firebase.auth.FirebaseAuth
import org.koin.compose.koinInject
import org.zayn.teamhub.feature.admin.DashBoardScreen
import org.zayn.teamhub.feature.admin.add_new_user.AddNewUserScreen
import org.zayn.teamhub.feature.admin.usersList.presentation.UsersListScreen
import org.zayn.teamhub.feature.home.ui.HomeScreen
import org.zayn.teamhub.feature.signIn.ui.SignInScreen
import org.zayn.teamhub.feature.work_summary.ui.WorkDaySummaryScreen

@Composable
fun App(auth: FirebaseAuth = koinInject()) {
    val navController = rememberNavController()
    val userId = auth.currentUser?.uid
    val isAuthenticated = remember { userId != null }

    MyAppTheme {
        Scaffold(
            bottomBar = { BottomNavigationBar(navController) }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                TeamHubNavigationHost(navController, isAuthenticated)
            }
        }
    }


}

@Composable
fun TeamHubNavigationHost(navController: NavHostController, isAuthenticated: Boolean) {
    NavHost(
        navController = navController,
        startDestination = if (isAuthenticated) Screen.Home.route else Screen.SignIn.route
    ) {
        composable(route = Screen.SignIn.route) {
            SignInScreen(
                onSignIn = { navController.navigate(Screen.Home.route) },
                onSignUp = { navController.navigate(Screen.SignUp.route) })
        }
        composable(route = Screen.Home.route) {
            HomeScreen()
        }
        composable(route = Screen.DashBoard.route) {
            DashBoardScreen(onUsersClick = { navController.navigate(Screen.UsersList.route) })
        }
        composable(route = Screen.UsersList.route) {
            UsersListScreen() {
                navController.navigate(Screen.WorkSummary.createRoute(it))
            }
        }
        composable(route = Screen.SignUp.route) {
            AddNewUserScreen(navigateHome = { Screen.Home.route })
        }

        composable(
            route = Screen.WorkSummary.route,
            arguments = Screen.WorkSummary.navArguments
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")
            if (userId != null) {
                WorkDaySummaryScreen(userId)
            }
        }
    }

}

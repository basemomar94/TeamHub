package org.zayn.teamhub.core.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.gitlive.firebase.auth.FirebaseAuth
import org.koin.compose.koinInject
import org.zayn.teamhub.feature.dashboard.DashBoardScreen
import org.zayn.teamhub.feature.sign_up.SignupScreen
import org.zayn.teamhub.feature.usersList.presentation.UsersListScreen
import org.zayn.teamhub.feature.home.ui.HomeScreen
import org.zayn.teamhub.feature.profile.ProfileAction
import org.zayn.teamhub.feature.profile.ProfileScreen
import org.zayn.teamhub.feature.signIn.ui.SignInScreen
import org.zayn.teamhub.feature.work_day_details.ui.WorkDayScreen
import org.zayn.teamhub.feature.work_summary.ui.WorkDaySummaryScreen

@Composable
fun App(auth: FirebaseAuth = koinInject()) {
    val navController = rememberNavController()
    val userId = auth.currentUser?.uid
    val isAuthenticated = remember { userId != null }
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val appBarTitle = getAppTitle(currentRoute)

    MyAppTheme {
        Scaffold(
            topBar = {
                BaseTopAppBar(
                    text = appBarTitle,
                    onNavigationClick = { navController.popBackStack() })
            },
            bottomBar = { BottomNavigationBar(navController) }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                TeamHubNavigationHost(navController, isAuthenticated)
            }
        }
    }


}

private fun getAppTitle(currentDestination: String?): String {
    return when (currentDestination) {
        Screen.Home.route -> "Home"
        Screen.UsersList.route -> "Users List"
        Screen.WorkSummary.route -> "Work Summary"
        Screen.WorkSessions.route -> "Day Sessions"
        Screen.SignIn.route -> "Sign In"
        Screen.SignUp.route -> "Sign up"
        else -> "Team Hub"
    }

}

@Composable
private fun TeamHubNavigationHost(navController: NavHostController, isAuthenticated: Boolean) {
    NavHost(
        navController = navController,
        startDestination = if (isAuthenticated) Screen.Home.route else Screen.SignIn.route
    ) {
        composable(route = Screen.SignIn.route) {
            SignInScreen(
                onSignIn = {
                    navController.navigate(route = Screen.Home.route) {
                        popUpTo(Screen.SignIn.route) { inclusive = true }
                    }
                },
                onSignUp = { navController.navigate(Screen.SignUp.route) })
        }
        composable(route = Screen.Home.route) {
            HomeScreen()
        }
        composable(route = Screen.Profile.route) {
            ProfileScreen() { item ->
                when (item.action) {
                    ProfileAction.LOG_OUT -> navController.navigate(route = Screen.SignIn.route)
                    ProfileAction.ATTENDANCE -> TODO()
                }
            }
        }
        composable(route = Screen.DashBoard.route) {
            DashBoardScreen(onUsersClick = { navController.navigate(Screen.UsersList.route) })
        }
        composable(route = Screen.UsersList.route) {
            UsersListScreen { id ->
                navController.navigate(Screen.WorkSummary.createRoute(id))
            }
        }
        composable(route = Screen.SignUp.route) {
            SignupScreen(navigateHome = { navController.navigate(Screen.Home.route) })
        }

        composable(
            route = Screen.WorkSummary.route,
            arguments = Screen.WorkSummary.navArguments
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")
            if (userId != null) {
                WorkDaySummaryScreen(
                    userId = userId,
                    onDayClick = {
                        navController.navigate(
                            Screen.WorkSessions.createRoute(
                                userId = it.userId,
                                startOfDay = it.startOfDay ?: -1L,
                                endOfDay = it.endOfDay ?: -1L
                            )
                        )
                    })
            }
        }
        composable(
            route = Screen.WorkSessions.route,
            arguments = Screen.WorkSessions.navArguments
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")
            val startOfDay = backStackEntry.arguments?.getLong("startOfDay")
            val endOfDay = backStackEntry.arguments?.getLong("endOfDay")
            if (userId != null) {
                WorkDayScreen(
                    userId = userId,
                    startOfDay = startOfDay ?: -1L,
                    endOfDay = endOfDay ?: -1L
                )
            }

        }
    }

}

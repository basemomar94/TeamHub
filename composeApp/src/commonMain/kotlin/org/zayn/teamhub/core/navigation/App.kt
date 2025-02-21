package org.zayn.teamhub.core.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.gitlive.firebase.auth.FirebaseAuth
import org.koin.compose.koinInject
import org.zayn.teamhub.core.utils.data_store.ISessionManager
import org.zayn.teamhub.feature.dashboard.DashBoardScreen
import org.zayn.teamhub.feature.edit_profile.ui.EditProfileScreen
import org.zayn.teamhub.feature.home.ui.HomeScreen
import org.zayn.teamhub.feature.profile.ui.ProfileAction
import org.zayn.teamhub.feature.profile.ui.ProfileScreen
import org.zayn.teamhub.feature.signIn.ui.SignInScreen
import org.zayn.teamhub.feature.sign_up.SignupScreen
import org.zayn.teamhub.feature.splash.SplashScreen
import org.zayn.teamhub.feature.usersList.presentation.UsersListScreen
import org.zayn.teamhub.feature.work_day_details.ui.WorkDayScreen
import org.zayn.teamhub.feature.work_summary.ui.WorkDaySummaryScreen

@Composable
fun App(auth: FirebaseAuth = koinInject(), sessionManager: ISessionManager = koinInject()) {
    val navController = rememberNavController()
    val userId = auth.currentUser?.uid
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val appBarTitle = getAppTitle(currentRoute)

    MyAppTheme {
        Scaffold(
            topBar = {
                if (currentRoute != Screen.Splash.route) {
                    BaseTopAppBar(
                        text = appBarTitle,
                        onNavigationClick = { navController.popBackStack() })
                }
            },
            bottomBar = {
                BottomNavigationBar(
                    navController = navController,
                    sessionManager = sessionManager
                )
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                TeamHubNavigationHost(navController, userId)
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
private fun TeamHubNavigationHost(navController: NavHostController, userId: String?) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
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
            ProfileScreen { item ->
                when (item.action) {
                    ProfileAction.LOG_OUT -> navController.navigate(route = Screen.SignIn.route) {
                        popUpTo(Screen.Profile.route) { inclusive = true }
                    }

                    ProfileAction.ATTENDANCE -> navController.navigate(
                        route = Screen.WorkSummary.createRoute(
                            userId
                        )
                    )

                    ProfileAction.EDIT_PROFILE -> navController.navigate(Screen.EditProfile.route)
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

        composable(route = Screen.Splash.route) {
            SplashScreen {
                navController.navigate(if (userId != null) Screen.Home.route else Screen.SignIn.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }

                }
            }
        }

        composable(route = Screen.EditProfile.route) {
            EditProfileScreen()
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

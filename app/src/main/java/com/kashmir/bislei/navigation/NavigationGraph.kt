package com.kashmir.bislei.navigation

import LoginScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import com.kashmir.bislei.screens.*

@Composable
fun NavigationGraph(navController: NavHostController, isUserLoggedIn: Boolean) {
    NavHost(navController = navController, startDestination = Screens.Splash.route) {

        composable(Screens.Splash.route) {
            SplashScreen(
                navController = navController,
                isUserLoggedIn = isUserLoggedIn
            )
        }

        composable(Screens.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screens.Home.route) {
                        popUpTo(Screens.Login.route) { inclusive = true }
                    }
                },
                onRegisterClick = {
                    navController.navigate(Screens.Register.route)
                },
                onForgotPassword = {
                    navController.navigate(Screens.ResetPassword.route)
                }
            )
        }

        composable(Screens.Register.route) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Screens.Login.route) {
                        popUpTo(Screens.Register.route) { inclusive = true }
                    }
                },
                onLoginClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screens.Home.route) {
            HomeScreen(
                onLogout = {
                    navController.navigate(Screens.Login.route) {
                        popUpTo(Screens.Home.route) { inclusive = true }
                    }
                },
                onBackClick = {
                    // Define the back navigation action
                    navController.popBackStack()
                },
                onProfileClick = {
                    // Navigate to the Profile screen or perform another action
                    navController.navigate(Screens.Profile.route)
                }
            )
        }


        composable(Screens.ResetPassword.route) {
            ResetPasswordScreen(
                onBackToLogin = {
                    navController.popBackStack()
                }
            )
        }
    }
}

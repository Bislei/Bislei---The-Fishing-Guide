package com.kashmir.bislei.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kashmir.bislei.screens.HomeScreen
import com.kashmir.bislei.screens.LoginScreen
import com.kashmir.bislei.screens.RegisterScreen
import com.kashmir.bislei.screens.SplashScreen

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screens.Splash.route) {

        composable(Screens.Splash.route) {
            SplashScreen(
                navController = navController,
                isUserLoggedIn = false // Replace with real login check from ViewModel
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
                        popUpTo(Screens.Home.route) { inclusive = true } // Clear Home from backstack
                    }
                }
            )
        }
    }
}

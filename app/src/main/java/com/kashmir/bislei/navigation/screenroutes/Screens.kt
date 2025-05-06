package com.kashmir.bislei.navigation.screenroutes

// Top-level navigation routes
sealed class Screens(val route: String) {
    object Splash : Screens("splash_screen")
    object Login : Screens("login_screen")
    object Register : Screens("register_screen")
    object Home : Screens("home_screen")
    object ResetPassword : Screens("reset_password_screen")
    // Removed Profile route as it's अब handled within HomeScreen's nested navigation
}

// Nested routes for HomeScreen's bottom navigation
sealed class HomeScreen(val route: String) {
    object Explore : HomeScreen("explore")
    object Identify : HomeScreen("identify")
    object Search : HomeScreen("search")
    object Competition : HomeScreen("competition")
    object Profile : HomeScreen("profile")
}
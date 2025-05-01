package com.kashmir.bislei.navigation

sealed class Screens(val route: String) {
    object Splash : Screens("splash_screen")
    object Login : Screens("login_screen")
    object Register : Screens("register_screen")
    object Home : Screens("home_screen")
    object ResetPassword : Screens("reset_password_screen")
    object Profile : Screens("profile") // Ensure this exists


}

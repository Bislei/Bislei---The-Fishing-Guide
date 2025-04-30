package com.kashmir.bislei.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.kashmir.bislei.navigation.Screens
import kotlinx.coroutines.delay
import androidx.navigation.NavController
import com.kashmir.bislei.R

@Composable
fun SplashScreen(navController: NavController, isUserLoggedIn: Boolean) {



    LaunchedEffect(Unit) {
        delay(0) // Optional: Add splash delay
        if (isUserLoggedIn) {
            navController.navigate(Screens.Home.route) {
                popUpTo(Screens.Splash.route) { inclusive = true }
            }
        } else {
            navController.navigate(Screens.Login.route) {
                popUpTo(Screens.Splash.route) { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo", Modifier.size(500.dp))
        }
    }
}

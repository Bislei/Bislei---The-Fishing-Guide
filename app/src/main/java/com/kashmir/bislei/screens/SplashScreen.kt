package com.kashmir.bislei.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.kashmir.bislei.navigation.Screens
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavHostController,
    isUserLoggedIn: Boolean
) {
    LaunchedEffect(true) {
        delay(2500)

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
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Bislei", style = MaterialTheme.typography.headlineSmall)
                Text(text = "The Fishing Guide 🎣", style = MaterialTheme.typography.headlineSmall)
            }
        }
    }
}

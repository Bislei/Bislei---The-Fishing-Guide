package com.kashmir.bislei.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kashmir.bislei.R
import com.kashmir.bislei.navigation.Screens
import kotlinx.coroutines.delay

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
    ) { Image(
        painter = painterResource(id = R.drawable.colored_skyblue_focus),
        contentDescription = "Fishing Background",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize(),
    )
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo", Modifier.size(500.dp))
        }
    }

}

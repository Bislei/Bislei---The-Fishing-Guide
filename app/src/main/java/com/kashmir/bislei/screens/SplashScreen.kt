package com.kashmir.bislei.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.kashmir.bislei.R
import com.kashmir.bislei.navigation.Screens
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavHostController,
    isUserLoggedIn: Boolean // from ViewModel later
) {
    LaunchedEffect(true) {
        delay(2500) // wait for 2.5 seconds

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
//            Icon(
//                painter = painterResource(id = R.drawable.ic_launcher_background), // Use logo here
//                contentDescription = "App Logo",
//                modifier = Modifier.size(100.dp)
//            )
            Spacer(modifier = Modifier.height(20.dp))
            Column (verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
            Text(text = "Bislei", style = MaterialTheme.typography.headlineSmall)
            Text(text = "The Fishing Guide \uD83C\uDFA3", style = MaterialTheme.typography.headlineSmall)

            }
        }
    }
}

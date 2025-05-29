package com.kashmir.bislei.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kashmir.bislei.navigation.screenroutes.Screens
import kotlinx.coroutines.delay
import com.airbnb.lottie.compose.*
import com.kashmir.bislei.R
import com.kashmir.bislei.ui.theme.AppTheme
import com.kashmir.bislei.ui.theme.Poppins

@Composable
fun SplashScreen(
    navController: NavHostController,
    isUserLoggedIn: Boolean
) {

    // Load Lottie animation
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.fish_login))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = true,
        iterations = LottieConstants.IterateForever
    )

    LaunchedEffect(true) {
        delay(3000)

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
            .fillMaxSize().background(AppTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Lottie Animation
            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier
                    .size(300.dp)


            )

            Text(
                text = "Bislei",
                color = AppTheme.colorScheme.primary,
                fontSize = 50.sp,
                fontFamily = Poppins,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "From Lakes to Likes",
                color = AppTheme.colorScheme.primary,
                fontSize = 20.sp,
                fontFamily = Poppins
            )
        }
    }

}

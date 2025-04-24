package com.kashmir.bislei

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.kashmir.bislei.navigation.NavigationGraph
import com.kashmir.bislei.ui.theme.BisleiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BisleiTheme {
                val navController = rememberNavController()

                // Check if user is already logged in and verified
                val isUserLoggedIn = remember {
                    mutableStateOf(
                        FirebaseAuth.getInstance().currentUser?.isEmailVerified == true
                    )
                }

                NavigationGraph(navController = navController, isUserLoggedIn = isUserLoggedIn.value)
            }
        }
    }
}

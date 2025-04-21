package com.kashmir.bislei.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onLogout: () -> Unit // Callback to navigate to login screen
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "This is the Home Screen", color = Color.Black)
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    FirebaseAuth.getInstance().signOut()
                    onLogout() // Navigate back to login
                }
            ) {
                Text("Logout")
            }
        }
    }
}

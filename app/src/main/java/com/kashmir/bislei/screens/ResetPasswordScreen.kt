package com.kashmir.bislei.screens

import android.util.Patterns
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kashmir.bislei.viewModels.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun ResetPasswordScreen(
    authViewModel: AuthViewModel = viewModel(),
    onBackToLogin: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    val isLoading = authViewModel.isLoading
    val message = authViewModel.errorMessage

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Reset Password", style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Enter your email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    coroutineScope.launch {
                        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            authViewModel.errorMessage = "Please enter a valid email"
                        } else {
                            authViewModel.sendPasswordReset(email)
                        }
                    }
                },
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isLoading) "Sending..." else "Send Reset Link")
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (message.isNotEmpty()) {
                Text(
                    text = message,
                    color = if (message.contains("sent", ignoreCase = true)) Color.DarkGray else Color.Red
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onBackToLogin) {
                Text("Back to Login")
            }
        }
    }
}

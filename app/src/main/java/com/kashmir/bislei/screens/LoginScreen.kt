package com.kashmir.bislei.screens


import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kashmir.bislei.viewModels.AuthViewModel

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel = viewModel(),
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(text = "Login", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                authViewModel.loginUser(email, password)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (authViewModel.errorMessage.isNotEmpty()) {
            Text(text = "Error: ${authViewModel.errorMessage}", color = MaterialTheme.colorScheme.error)
        }

        // Navigate to Home if login successful
        if (authViewModel.isLoggedIn) {
            onLoginSuccess()
        }

        if (authViewModel.isLoggedIn) {
            onLoginSuccess()
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Add Register Navigation Text
        TextButton(onClick = onRegisterClick) {
            Text("Don't have an account? Register here")
        }
    }
}


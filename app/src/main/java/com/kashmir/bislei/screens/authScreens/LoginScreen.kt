package com.kashmir.bislei.screens.authScreens

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kashmir.bislei.viewModels.AuthViewModel
import kotlinx.coroutines.launch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel = viewModel(),
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit,
    onForgotPassword: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var infoMessage by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
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
                label = { Text("Password") },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = icon, contentDescription = if (passwordVisible) "Hide password" else "Show password")
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    coroutineScope.launch {
                        if (email.isEmpty() || password.isEmpty()) {
                            errorMessage = "Please fill all the fields"
                        } else {
                            errorMessage = ""
                            infoMessage = ""
                            authViewModel.loginUser(email, password)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !authViewModel.isLoading
            ) {
                Text(if (authViewModel.isLoading) "Logging in..." else "Login")
            }

            // Call the onForgotPassword function when the "Forgot Password?" button is clicked
            TextButton(
                onClick = {
                    if (email.isEmpty()) {
                        errorMessage = "Please enter your email to reset password"
                    } else {
                        coroutineScope.launch {
                            authViewModel.sendPasswordReset(email)
                            infoMessage = "Reset link sent to $email"
                            errorMessage = ""
                        }
                    }
                    onForgotPassword() // Trigger the onForgotPassword callback
                }
            ) {
                Text("Forgot Password?")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Error Message
            if (errorMessage.isNotEmpty()) {
                Text(text = "Error: $errorMessage", color = MaterialTheme.colorScheme.error)
            }

            // Success Info Message
            if (infoMessage.isNotEmpty()) {
                Text(text = infoMessage, color = MaterialTheme.colorScheme.primary)
            }

            if (authViewModel.errorMessage.isNotEmpty()) {
                Text(
                    text = "Error: ${authViewModel.errorMessage}",
                    color = MaterialTheme.colorScheme.error
                )
            }

            if (authViewModel.isLoggedIn) {
                LaunchedEffect(Unit) {
                    onLoginSuccess()
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            TextButton(onClick = onRegisterClick) {
                Text("Don't have an account? Register here")
            }
        }
    }
}

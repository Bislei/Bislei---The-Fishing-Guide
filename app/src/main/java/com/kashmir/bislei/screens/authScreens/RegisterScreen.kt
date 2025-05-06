package com.kashmir.bislei.screens.authScreens

import android.util.Patterns
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kashmir.bislei.viewModels.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    authViewModel: AuthViewModel = viewModel(),
    onRegisterSuccess: () -> Unit,
    onLoginClick: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    var localErrorMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Register", style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(value = name,
                onValueChange = { name = it },
                label = { Text("Full Name") }
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(value = phone,
                onValueChange = { phone = it },
                label = { Text("Phone") }
            )
            Spacer(modifier = Modifier.height(12.dp))

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

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password") }
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Show local error messages
            if (localErrorMessage.isNotEmpty()) {
                Text(
                    text = localErrorMessage,
                    color = Color.Red,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            // Show Firebase error message
            if (authViewModel.errorMessage.isNotEmpty()) {
                Text(
                    text = "Error: ${authViewModel.errorMessage}",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            // Register Button with loader
            Button(
                onClick = {
                    coroutineScope.launch {
                        localErrorMessage = ""
                        isLoading = true

                        // Basic Validation
                        when {
                            name.isBlank() || phone.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank() -> {
                                localErrorMessage = "Please fill all the fields"
                            }

                            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                                localErrorMessage = "Invalid email format"
                            }

                            phone.length != 10 || !phone.all { it.isDigit() } -> {
                                localErrorMessage = "Invalid phone number"
                            }

                            password != confirmPassword -> {
                                localErrorMessage = "Passwords do not match"
                            }

                            password.length < 6 -> {
                                localErrorMessage = "Password must be at least 6 characters"
                            }

                            else -> {
                                localErrorMessage = ""
                                authViewModel.registerUserWithDetails(name, phone, email, password)
                            }
                        }

                        isLoading = false
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text("Register")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Success state
            if (authViewModel.errorMessage.contains("Please verify your email", ignoreCase = true)) {
                Text(
                    text = "Registration successful! Please verify your email.",
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            if (authViewModel.isLoggedIn) {
                onRegisterSuccess()
            }

            //Back to login
            TextButton(onClick = onLoginClick) {
                Text("Already have an account? Login here")
            }
        }
    }
}
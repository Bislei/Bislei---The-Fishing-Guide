package com.kashmir.bislei.screens.authScreens

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.kashmir.bislei.R
import com.kashmir.bislei.viewModels.AuthViewModel
import kotlinx.coroutines.launch
import com.airbnb.lottie.compose.*
import com.kashmir.bislei.ui.theme.AppTheme
import com.kashmir.bislei.ui.theme.Poppins

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

    // Load Lottie animation
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.fish_login))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = true,
        iterations = LottieConstants.IterateForever
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize().background(AppTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Lottie Animation
            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier
                    .size(200.dp)
                    .padding(bottom = 3.dp)
            )

            Text(
                text = "Register",
                color = AppTheme.colorScheme.primary,
                fontSize = 30.sp,
                fontFamily = Poppins,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = {
                    Text(
                        "Full Name",
                        color = AppTheme.colorScheme.primary,
                        fontFamily = Poppins
                    )
                },
                textStyle = TextStyle(color = AppTheme.colorScheme.primary),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = Color.Unspecified,
                    unfocusedLabelColor = Color.Gray,
                    focusedBorderColor = AppTheme.colorScheme.primary,
                    unfocusedBorderColor = Color.Gray,
                    errorBorderColor = Color.Red // Outline color when isError = true
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = {
                    Text(
                        "Phone",
                        color = AppTheme.colorScheme.primary,
                        fontFamily = Poppins
                    )
                },
                textStyle = TextStyle(color = AppTheme.colorScheme.primary),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = Color.Unspecified,
                    unfocusedLabelColor = Color.Gray,
                    focusedBorderColor = AppTheme.colorScheme.primary,
                    unfocusedBorderColor = Color.Gray,
                    errorBorderColor = Color.Red // Outline color when isError = true
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = {
                    Text(
                        "Email",
                        color = AppTheme.colorScheme.primary,
                        fontFamily = Poppins
                    )
                },
                textStyle = TextStyle(color = AppTheme.colorScheme.primary),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = Color.Unspecified,
                    unfocusedLabelColor = Color.Gray,
                    focusedBorderColor = AppTheme.colorScheme.primary,
                    unfocusedBorderColor = Color.Gray,
                    errorBorderColor = Color.Red // Outline color when isError = true
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = {
                    Text(
                        "Password",
                        color = AppTheme.colorScheme.primary,
                        fontFamily = Poppins
                    )
                },
                textStyle = TextStyle(color = AppTheme.colorScheme.primary),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AppTheme.colorScheme.primary,
                    unfocusedBorderColor = AppTheme.colorScheme.primary,
                    errorBorderColor = Color.Red // Outline color when isError = true
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = {
                    Text(
                        "Confirm Password",
                        color = AppTheme.colorScheme.primary,
                        fontFamily = Poppins
                    )
                },
                textStyle = TextStyle(color = AppTheme.colorScheme.primary),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = Color.Unspecified,
                    unfocusedLabelColor = Color.Gray,
                    focusedBorderColor = AppTheme.colorScheme.primary,
                    unfocusedBorderColor = Color.Gray,
                    errorBorderColor = Color.Red // Outline color when isError = true
                ),
                modifier = Modifier.fillMaxWidth()
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
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppTheme.colorScheme.primary,
                    contentColor = AppTheme.colorScheme.onPrimary
                ),
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
                Text(
                    text = "Register",
                    fontFamily = Poppins,
//                    fontWeight = FontWeight.Bold,
                    color = AppTheme.colorScheme.background,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Success state
            if (authViewModel.errorMessage.contains(
                    "Please verify your email",
                    ignoreCase = true
                )
            ) {
                Text(
                    text = "Registration successful! Please verify your email.",
                    color = MaterialTheme.colorScheme.primary
                )
            }

            if (authViewModel.isLoggedIn) {
                onRegisterSuccess()
            }

            //Back to login

            Row(
                modifier = Modifier.fillMaxWidth()
                    .offset(y = (-20).dp), // Pull closer by 20dp
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            )
            {
                TextButton(onClick = onLoginClick)
                {
                    Text(
                        "Already have an account? Login here",
                        color=(AppTheme.colorScheme.primary), fontSize = 17.sp)
                }
            }
        }
    }
}
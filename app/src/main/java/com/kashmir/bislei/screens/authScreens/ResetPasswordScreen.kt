package com.kashmir.bislei.screens.authScreens

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kashmir.bislei.R
import com.kashmir.bislei.viewModels.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun ResetPasswordScreen(
    authViewModel: AuthViewModel = viewModel(),
    onBackToLogin: () -> Unit
) {
    val primaryGreen = Color(0xFF2E8B57)

    var email by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    val isLoading = authViewModel.isLoading
    val message = authViewModel.errorMessage

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.colored_skyblue_focus),
            contentDescription = "Fishing Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0f))
        )

        Column(
            modifier = Modifier
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Reset Password",
                style = MaterialTheme.typography.headlineLarge,
                color = primaryGreen
            )
            Spacer(modifier = Modifier.height(20.dp))

            val textFieldColors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = primaryGreen,
                unfocusedBorderColor = primaryGreen,
                focusedLabelColor = primaryGreen,
                cursorColor = primaryGreen
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Enter your email") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = textFieldColors
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
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = primaryGreen)
            ) {
                Text(
                    if (isLoading) "Sending..." else "Send Reset Link",
                    fontSize = 16.sp
                )
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
                Text(
                    "Back to Login",
                    style = MaterialTheme.typography.bodyLarge.copy(color = primaryGreen)
                )
            }
        }
    }
}

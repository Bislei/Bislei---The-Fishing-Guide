package com.kashmir.bislei.screens.authScreens

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kashmir.bislei.viewModels.AuthViewModel
import kotlinx.coroutines.launch
import com.airbnb.lottie.compose.*
import com.kashmir.bislei.R
import com.kashmir.bislei.ui.theme.AppTheme
import com.kashmir.bislei.ui.theme.Poppins

@Composable
fun ResetPasswordScreen(
    authViewModel: AuthViewModel = viewModel(),
    onBackToLogin: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    val isLoading = authViewModel.isLoading
    val message = authViewModel.errorMessage

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
            modifier = Modifier  .offset(y = (-80).dp)  // Pull closer by 20dp
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Lottie Animation
            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier
                    .size(300.dp)
                    .padding(bottom = 2.dp)
            )

            Text("Reset Password",
                color = AppTheme.colorScheme.primary, fontSize = 30.sp,fontFamily = Poppins, fontWeight = FontWeight.SemiBold,
            )

            Spacer(modifier = Modifier.height(30.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Enter your email",color = AppTheme.colorScheme.primary,fontFamily = Poppins) },
                textStyle = TextStyle(color = AppTheme.colorScheme.primary),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = Color.Unspecified,
                    unfocusedLabelColor = Color.Gray,
                    focusedBorderColor = AppTheme.colorScheme.primary,
                    unfocusedBorderColor =Color.Gray,
                    errorBorderColor = Color.Red // Outline color when isError = true
                ),
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
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppTheme.colorScheme.primary,
                    contentColor =AppTheme.colorScheme.onPrimary
                )
            ) {
                Text(if (isLoading) "Sending..." else "Reset" ,fontFamily = Poppins,
//                    fontWeight = FontWeight.Bold,
                    color = AppTheme.colorScheme.background,
                    fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (message.isNotEmpty()) {
                Text(
                    text = message,
                    color = if (message.contains("Sent", ignoreCase = true)) Color.DarkGray else Color.Red
                )
            }

            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
                    .offset(y = (-25).dp) , // Pull closer by 20dp
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            )
            {
                TextButton(onClick = onBackToLogin) {
                    Text("Back to Login", color = (AppTheme.colorScheme.primary), fontSize = 17.sp)
                }
            }
        }
    }
}

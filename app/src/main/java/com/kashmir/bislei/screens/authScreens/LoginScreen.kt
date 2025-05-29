        package com.kashmir.bislei.screens.authScreens

        import androidx.compose.foundation.Image
        import androidx.compose.foundation.background
        import androidx.compose.foundation.layout.*
        import androidx.compose.foundation.text.KeyboardOptions
        import androidx.compose.material.icons.Icons
        import androidx.compose.material.icons.filled.Visibility
        import androidx.compose.material.icons.filled.VisibilityOff
        import androidx.compose.material3.*
        import androidx.compose.runtime.*
        import androidx.compose.ui.*
        import androidx.compose.ui.graphics.Brush
        import androidx.compose.ui.graphics.Color
        import androidx.compose.ui.layout.ContentScale
        import androidx.compose.ui.res.painterResource
        import androidx.compose.ui.text.TextStyle
        import androidx.compose.ui.text.font.Font
        import androidx.compose.ui.text.font.FontStyle
        import androidx.compose.ui.text.font.FontWeight
        import androidx.compose.ui.text.input.*
        import androidx.compose.ui.unit.dp
        import androidx.compose.ui.unit.max
        import androidx.compose.ui.unit.sp
        import androidx.lifecycle.viewmodel.compose.viewModel
        import com.kashmir.bislei.viewModels.AuthViewModel
        import kotlinx.coroutines.launch
        import com.airbnb.lottie.compose.*
        import com.kashmir.bislei.R
        import com.kashmir.bislei.ui.theme.AppTheme
        import com.kashmir.bislei.ui.theme.Poppins

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


            // Load Lottie animation
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.fish_login))
            val progress by animateLottieCompositionAsState(
                composition = composition,
                isPlaying = true,
                iterations = LottieConstants.IterateForever
            )


            Box(
                modifier = Modifier.fillMaxSize()
                    .background(AppTheme.colorScheme.background)

            ) {

                // Foreground UI
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // Lottie Animation
                    LottieAnimation(
                        composition = composition,
                        progress = { progress },
                        modifier = Modifier
                            .size(300.dp)
                            .padding(bottom = 15.dp)
                    )


                    Spacer(modifier = Modifier.height(30.dp))
                    Text(text = "Login",
                        color = AppTheme.colorScheme.primary, fontSize = 30.sp,fontFamily = Poppins, fontWeight = FontWeight.SemiBold,
                     )

                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = email,
                        maxLines = 1,
                        onValueChange = {
                            email = it
                            errorMessage = ""
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedLabelColor = Color.Unspecified,
                            unfocusedLabelColor = Color.Gray,
                            focusedBorderColor = AppTheme.colorScheme.primary,
                            unfocusedBorderColor =Color.Gray,
                            errorBorderColor = Color.Red // Outline color when isError = true
                        ),
                        label = { Text("Email",fontFamily = Poppins) },
                        textStyle = TextStyle(color = AppTheme.colorScheme.primary)
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = password,
                        maxLines = 1,
                        onValueChange = {
                            password = it
                            errorMessage = ""
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedLabelColor = Color.Unspecified,
                            unfocusedLabelColor = Color.Gray,
                            focusedBorderColor = AppTheme.colorScheme.primary,
                            unfocusedBorderColor =Color.Gray,
                            errorBorderColor = Color.Red // Outline color when isError = true
                        ),
                        label = { Text("Password",fontFamily = Poppins) },
                        textStyle = TextStyle(color = AppTheme.colorScheme.primary),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val icon = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(imageVector = icon, contentDescription = if (passwordVisible) "Hide password" else "Show password",)
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))


//                    Spacer(modifier = Modifier.height(2.dp))

                    if (errorMessage.isNotEmpty()) {
                        Text(text = "Error: $errorMessage", color = MaterialTheme.colorScheme.error)
                    }

                    if (infoMessage.isNotEmpty()) {
                        Text(text = infoMessage, color = AppTheme.colorScheme.primary)
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


                    Button(
                        onClick = {
                            coroutineScope.launch {
                                if (email.isEmpty() || password.isEmpty()) {
                                    errorMessage = "Please Fill all the Fields"
                                } else {
                                    errorMessage = ""
                                    infoMessage = ""
                                    authViewModel.loginUser(email, password)
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !authViewModel.isLoading,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AppTheme.colorScheme.primary,
                            contentColor =AppTheme.colorScheme.onPrimary
                        )
                    )
                    {
                        Text(
                            if (authViewModel.isLoading) "Logging in..." else "Login",
                            fontFamily = Poppins,
//                            fontWeight = FontWeight.Bold,
                            color = AppTheme.colorScheme.background,
                            fontSize = 16.sp
                        )
                    }

                    TextButton(
                        onClick = {
                            if (email.isEmpty()) {
                                errorMessage = "Please enter your email to reset password"
                            } else {
                                coroutineScope.launch {
                                    authViewModel.sendPasswordReset(email)
                                    infoMessage = "Reset link sent to $email"
                                    errorMessage = ""
                                    onForgotPassword()
                                }
                            }
                        }
                    ) {
                        Text("Forgot Password?", color=(AppTheme.colorScheme.primary), fontSize = 17.sp)
                    }

                    Spacer(modifier = Modifier.height(1.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth()
                      .offset(y = (-20).dp) , // Pull closer by 20dp
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    )
                    {

                           TextButton(onClick = onRegisterClick
                           ) {
                               Text("Don't have an account? Register here", color=(AppTheme.colorScheme.primary), fontSize = 17.sp)
                       }
                    }

                }
            }
        }

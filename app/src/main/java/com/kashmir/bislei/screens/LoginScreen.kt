@file:OptIn(ExperimentalMaterial3Api::class)

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.painterResource
import com.kashmir.bislei.R
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight

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
    val emailFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val scrollState = rememberScrollState()

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.colored_skyblue_focus),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp)
                .verticalScroll(scrollState)
                .imePadding(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Login",
                style = MaterialTheme.typography.headlineLarge,
                color =Color(0xFF2E8B57),
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Email Input Field
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { passwordFocusRequester.requestFocus() }),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .focusRequester(emailFocusRequester),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF2E8B57),
                    focusedLabelColor = Color(0xFF2E8B57),
                    cursorColor = Color(0xFF2E8B57)
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Password Input Field
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    val description = if (passwordVisible) "Hide password" else "Show password"
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = icon, contentDescription = description)
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .focusRequester(passwordFocusRequester),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF2E8B57),
                    focusedLabelColor = Color(0xFF2E8B57),
                    cursorColor = Color(0xFF2E8B57)
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                enabled = !authViewModel.isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2E8B57),
                    contentColor = Color.White
                )
            ) {
                Text(
                    if (authViewModel.isLoading) "Logging in..." else "Login",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.ExtraBold)
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
                        }
                    }
                    onForgotPassword()
                },
                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF2E8B57))
            ) {
                Text("Forgot Password?", style = MaterialTheme.typography.bodyLarge)
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (errorMessage.isNotEmpty()) {
                Text(text = "Error: $errorMessage", color = MaterialTheme.colorScheme.error)
            }

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

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Don't have an account?")
                    Spacer(modifier = Modifier.width(4.dp))
                    TextButton(
                        contentPadding = PaddingValues(0.dp),
                        onClick = onRegisterClick
                    ) {
                        Text(
                            text = "Register here",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color(0xFF2E8B57)
                        )
                    }
                }
            }
        }
    }
}

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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.layout.ContentScale

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

    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        // Background SVG image
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

            // Logo Image
//            Image(
//                painter = painterResource(id = R.drawable.ini),
//                contentDescription = "App Logo",
//                modifier = Modifier
//                    .size(500.dp)
//                    .clip(RoundedCornerShape(16.dp))
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Login", style = MaterialTheme.typography.headlineLarge)

            Spacer(modifier = Modifier.height(12.dp))

            // Email Input Field
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = { passwordFocusRequester.requestFocus() }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .focusRequester(emailFocusRequester)
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
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .focusRequester(passwordFocusRequester)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Login Button
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

            // Forgot Password Button
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
                }
            ) {
                Text("Forgot Password?")
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Error Message
            if (errorMessage.isNotEmpty()) {
                Text(text = "Error: $errorMessage", color = MaterialTheme.colorScheme.error)
            }

            // Info Message
            if (infoMessage.isNotEmpty()) {
                Text(text = infoMessage, color = MaterialTheme.colorScheme.primary)
            }

            // Auth ViewModel Error Message
            if (authViewModel.errorMessage.isNotEmpty()) {
                Text(
                    text = "Error: ${authViewModel.errorMessage}",
                    color = MaterialTheme.colorScheme.error
                )
            }

            // After successful login
            if (authViewModel.isLoggedIn) {
                LaunchedEffect(Unit) {
                    onLoginSuccess()
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Register Section
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
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}

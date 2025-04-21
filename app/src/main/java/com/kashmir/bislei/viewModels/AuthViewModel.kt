package com.kashmir.bislei.viewModels

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.coroutines.tasks.await

class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    var isLoggedIn by mutableStateOf(false)
    var errorMessage by mutableStateOf("")
    var isLoading by mutableStateOf(false)

    // Register User and Send Email Verification
    suspend fun registerUser(email: String, password: String) {
        isLoading = true
        try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user

            if (user != null) {
                user.sendEmailVerification().await()
                errorMessage = "Verification email sent to $email. Please verify your email."
                isLoggedIn = false // Not logged in until email is verified
            } else {
                errorMessage = "Registration failed. Try again."
            }
        } catch (e: FirebaseAuthUserCollisionException) {
            errorMessage = "Email is already registered. Please login or use a different email."
        } catch (e: Exception) {
            errorMessage = e.localizedMessage ?: "Something went wrong"
        }
        isLoading = false
    }

    // Login User and Check Email Verification Status
    suspend fun loginUser(email: String, password: String) {
        isLoading = true
        try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val user = result.user

            if (user?.isEmailVerified == true) {
                isLoggedIn = true
                errorMessage = ""
            } else {
                errorMessage = "Please verify your email before logging in."
                isLoggedIn = false
            }
        } catch (e: Exception) {
            errorMessage = e.localizedMessage ?: "Login failed. Check credentials."
        }
        isLoading = false
    }

    // Optional: Logout function
    fun logoutUser() {
        auth.signOut()
        isLoggedIn = false
    }
}

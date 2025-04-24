package com.kashmir.bislei.viewModels

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    var isLoggedIn by mutableStateOf(false)
    var errorMessage by mutableStateOf("")
    var isLoading by mutableStateOf(false)

    // Register User + Send Verification + Save Data
    suspend fun registerUserWithDetails(name: String, phone: String, email: String, password: String) {
        isLoading = true
        try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user

            if (user != null) {
                // Send email verification
                user.sendEmailVerification().await()

                // Save user info to FireStore
                val userMap = hashMapOf(
                    "uid" to user.uid,
                    "name" to name,
                    "phone" to phone,
                    "email" to email
                )
                firestore.collection("users").document(user.uid).set(userMap).await()

                errorMessage = "Verification email sent to $email. Please verify your email."
                isLoggedIn = false
            } else {
                errorMessage = "Registration failed. Try again."
            }
        } catch (e: FirebaseAuthUserCollisionException) {
            errorMessage = "Email already registered. Please login or use a different email."
        } catch (e: Exception) {
            errorMessage = e.localizedMessage ?: "Something went wrong"
        }
        isLoading = false
    }

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

    suspend fun sendPasswordReset(email: String) {
        isLoading = true
        try {
            auth.sendPasswordResetEmail(email).await()
            errorMessage = "Password reset email sent to $email"
        } catch (e: Exception) {
            errorMessage = e.localizedMessage ?: "Failed to send reset email"
        }
        isLoading = false
    }
}

package com.kashmir.bislei.viewModels


import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    var isLoading by mutableStateOf(false)
    var isLoggedIn by mutableStateOf(false)
    var errorMessage by mutableStateOf("")

    fun registerUser(email: String, password: String) {
        isLoading = true
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                isLoading = false
                isLoggedIn = task.isSuccessful
                if (!task.isSuccessful) {
                    errorMessage = task.exception?.message ?: "Registration failed"
                }
            }
    }

    fun loginUser(email: String, password: String) {
        isLoading = true
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                isLoading = false
                isLoggedIn = task.isSuccessful
                if (!task.isSuccessful) {
                    errorMessage = task.exception?.message ?: "Login failed"
                }
            }
    }
}

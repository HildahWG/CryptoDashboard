package com.example.cryptodashboard.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    fun login(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                _isLoggedIn.value = true
                onResult(true, null)
            }
            .addOnFailureListener {
                onResult(false, it.message)
            }
    }

    fun register(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                _isLoggedIn.value = true
                onResult(true, null)
            }
            .addOnFailureListener {
                onResult(false, it.message)
            }
    }
}

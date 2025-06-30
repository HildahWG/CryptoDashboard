package com.example.cryptodashboard.auth.domain

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    val currentUser: FirebaseUser?
    suspend fun login(email: String, password: String)
    suspend fun signUp(email: String, password: String)
}
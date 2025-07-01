package com.example.cryptodashboard.auth.di

import com.example.cryptodashboard.auth.data.remote.AuthRepositoryImpl
import com.example.cryptodashboard.auth.domain.AuthRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class AuthModule {
    @Provides
    fun providesFirebaseAuth() = Firebase.auth

    @Provides
    @ViewModelScoped
    fun providesAuthRepository(
        auth: FirebaseAuth
    ): AuthRepository = AuthRepositoryImpl(auth = auth)
}
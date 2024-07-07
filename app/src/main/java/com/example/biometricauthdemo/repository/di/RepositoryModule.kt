package com.example.biometricauthdemo.repository.di

import com.example.biometricauthdemo.repository.AuthenticationRepository
import com.example.biometricauthdemo.repository.AuthenticationRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@[Module InstallIn(SingletonComponent::class)]
interface RepositoryModule {


    @get:Binds
    val AuthenticationRepositoryImpl.authenticationRepository: AuthenticationRepository
}
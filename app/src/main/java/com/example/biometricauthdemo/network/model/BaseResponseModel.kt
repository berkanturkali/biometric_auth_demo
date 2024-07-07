package com.example.biometricauthdemo.network.model

data class BaseResponseModel<T>(
    val status: String,
    val message: String? = null,
    val data: T? = null
)
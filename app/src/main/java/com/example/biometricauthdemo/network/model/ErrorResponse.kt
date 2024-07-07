package com.example.biometricauthdemo.network.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ErrorResponse(
    @JsonProperty("status")
    val status: String,
    @JsonProperty("message")
    val message: String? = null,
)
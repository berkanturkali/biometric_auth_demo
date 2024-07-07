package com.example.biometricauthdemo

data class CiphertextWrapper(
    val cipherText: ByteArray,
    val initializationVector: ByteArray
)
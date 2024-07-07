package com.example.biometricauthdemo

import android.os.Build

object DeviceManager {

    private const val GOOGLE_SDK = "google_sdk"

    fun checkTheDeviceIsEmulator(): Boolean {
        return GOOGLE_SDK == Build.PRODUCT
    }
}
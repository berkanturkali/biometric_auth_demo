package com.example.biometricauthdemo.screen.common.usecase

import android.util.Patterns
import javax.inject.Inject

class CheckIfTheEmailAddressIsValidUseCase @Inject constructor() {

    operator fun invoke(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
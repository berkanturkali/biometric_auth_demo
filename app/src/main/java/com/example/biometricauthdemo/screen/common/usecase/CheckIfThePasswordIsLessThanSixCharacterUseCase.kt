package com.example.biometricauthdemo.screen.common.usecase

import javax.inject.Inject

class CheckIfThePasswordIsLessThanSixCharacterUseCase @Inject constructor() {

    operator fun invoke(password: String): Boolean {
        return password.length < 6
    }
}
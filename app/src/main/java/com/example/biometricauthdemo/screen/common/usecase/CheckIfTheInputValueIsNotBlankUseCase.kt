package com.example.biometricauthdemo.screen.common.usecase

import javax.inject.Inject

class CheckIfTheInputValueIsNotBlankUseCase @Inject constructor() {

    operator fun invoke(inputValue: String): Boolean {
        return inputValue.isNotBlank()
    }
}
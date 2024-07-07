package com.example.biometricauthdemo.screen.signup.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.biometricauthdemo.R
import com.example.biometricauthdemo.Resource
import com.example.biometricauthdemo.network.model.BaseResponseModel
import com.example.biometricauthdemo.network.model.SignupRequestModel
import com.example.biometricauthdemo.repository.AuthenticationRepository
import com.example.biometricauthdemo.screen.common.usecase.CheckIfTheEmailAddressIsValidUseCase
import com.example.biometricauthdemo.screen.common.usecase.CheckIfTheInputValueIsNotBlankUseCase
import com.example.biometricauthdemo.screen.common.usecase.CheckIfThePasswordIsLessThanSixCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupScreenViewModel @Inject constructor(
    private val checkIfTheInputValueIsNotBlankUseCase: CheckIfTheInputValueIsNotBlankUseCase,
    private val checkIfTheEmailAddressIsValidUseCase: CheckIfTheEmailAddressIsValidUseCase,
    private val checkIfThePasswordIsLessThanSixCharacterUseCase: CheckIfThePasswordIsLessThanSixCharacterUseCase,
    private val authenticationRepository: AuthenticationRepository,
) : ViewModel() {

    var email by mutableStateOf("")

    var password by mutableStateOf("")

    var emailValidationErrorMessage by mutableStateOf<String?>(null)

    var passwordValidationErrorMessage by mutableStateOf<String?>(null)

    private val _signupResponse = MutableLiveData<Resource<BaseResponseModel<Any?>>>()
    val signupResponse: LiveData<Resource<BaseResponseModel<Any?>>> get() = _signupResponse

    var showErrorDialog by mutableStateOf(false)


    fun signup() {
        viewModelScope.launch(Dispatchers.Main) {
            authenticationRepository.signup(SignupRequestModel(email = email, password = password))
                .collect { resource ->
                    showErrorDialog = resource is Resource.Error
                    _signupResponse.value = resource
                }
        }
    }

    fun validateEmail(context: Context): Boolean {
        val isEmailNotBlank = checkIfTheInputValueIsNotBlankUseCase(email)
        if (!isEmailNotBlank) {
            emailValidationErrorMessage =
                context.getString(R.string.input_field_empty_error_message)
            return false
        }
        val isEmailValid = checkIfTheEmailAddressIsValidUseCase(email)
        if (!isEmailValid) {
            emailValidationErrorMessage =
                context.getString(R.string.email_address_is_not_valid_error_message)
            return false
        }
        emailValidationErrorMessage = null
        return true
    }

    fun validatePassword(context: Context): Boolean {
        val isPasswordNotBlank = checkIfTheInputValueIsNotBlankUseCase(password)
        if (!isPasswordNotBlank) {
            passwordValidationErrorMessage =
                context.getString(R.string.input_field_empty_error_message)
            return false
        }
        val isPasswordLessThanSixCharacters = checkIfThePasswordIsLessThanSixCharacterUseCase(password)
        if (isPasswordLessThanSixCharacters) {
            passwordValidationErrorMessage =
                context.getString(R.string.password_can_not_be_less_than_six_chars_error_message)
            return false
        }
        passwordValidationErrorMessage = null
        return true
    }
}
package com.example.biometricauthdemo.screen.login.viewmodel

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.biometricauthdemo.BiometricPromptUtils
import com.example.biometricauthdemo.Constants
import com.example.biometricauthdemo.CryptographyManager
import com.example.biometricauthdemo.R
import com.example.biometricauthdemo.Resource
import com.example.biometricauthdemo.network.model.BaseResponseModel
import com.example.biometricauthdemo.network.model.LoginRequestModel
import com.example.biometricauthdemo.network.model.TokenResponseModel
import com.example.biometricauthdemo.repository.AuthenticationRepository
import com.example.biometricauthdemo.screen.common.usecase.CheckIfTheEmailAddressIsValidUseCase
import com.example.biometricauthdemo.screen.common.usecase.CheckIfTheInputValueIsNotBlankUseCase
import com.example.biometricauthdemo.screen.common.usecase.CheckIfThePasswordIsLessThanSixCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "LoginScreenViewModel"

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val checkIfTheInputValueIsNotBlankUseCase: CheckIfTheInputValueIsNotBlankUseCase,
    private val checkIfTheEmailAddressIsValidUseCase: CheckIfTheEmailAddressIsValidUseCase,
    private val checkIfThePasswordIsLessThanSixCharacterUseCase: CheckIfThePasswordIsLessThanSixCharacterUseCase,
    private val authenticationRepository: AuthenticationRepository,
) : ViewModel() {

    var email by mutableStateOf("")

    var password by mutableStateOf("")

    var emailValidationErrorMessage by mutableStateOf<String?>(null)

    var passwordValidationErrorMessage by mutableStateOf<String?>(null)

    private val _loginResponse = MutableLiveData<Resource<BaseResponseModel<TokenResponseModel>>>()

    val loginResponse: LiveData<Resource<BaseResponseModel<TokenResponseModel>>> get() = _loginResponse

    var showErrorDialog by mutableStateOf(false)

    private val cryptographyManager = CryptographyManager()

    private val _navigateUserToSettingsToEnrollBiometricAuthentication = Channel<Boolean>()

    val navigateUserToSettingsToEnrollBiometricAuthentication =
        _navigateUserToSettingsToEnrollBiometricAuthentication.receiveAsFlow()

    var token: String? = null

    fun login(context: Context) {
        viewModelScope.launch(Dispatchers.Main) {
            authenticationRepository.login(LoginRequestModel(email, password)).collect {
                showErrorDialog = it is Resource.Error
                if (it is Resource.Success) {
                    token = it.data?.data?.token
                    token?.let { token ->
                        showBiometricPromptForEncryption(context, token)
                    }
                }
                _loginResponse.value = it
            }
        }
    }

    fun showBiometricPromptForEncryption(context: Context, token: String, onSuccessCallback:(() -> Unit)? = null) {
        val canAuthenticate = BiometricManager.from(context).canAuthenticate()
        if (canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS) {
            val cipher = cryptographyManager.getInitializedCipherForEncryption()
            val biometricPrompt =
                BiometricPromptUtils.createBiometricPrompt(context as AppCompatActivity) { result ->
                    encryptAndStoreServerToken(context, token, result, onSuccessCallback)
                }
            val promptInfo = BiometricPromptUtils.createPromptInfo(context)
            biometricPrompt.authenticate(promptInfo, BiometricPrompt.CryptoObject(cipher))
        } else {
            if (canAuthenticate == BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE) {
                Log.i(TAG, "showBiometricPromptForEncryption: there is no biometric hardware")
                return
            }
            if (canAuthenticate == BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED) {
                Log.i(
                    TAG,
                    "showBiometricPromptForEncryption: The user does not have any biometrics enrolled. Navigating settings to enroll"
                )
                _navigateUserToSettingsToEnrollBiometricAuthentication.trySend(true)
            }
        }
    }

    private fun encryptAndStoreServerToken(
        context: Context,
        token: String,
        authResult: BiometricPrompt.AuthenticationResult,
        onSuccessCallback:(() -> Unit)? = null
    ) {
        authResult.cryptoObject?.cipher?.apply {
            val encryptedServerTokenWrapper = cryptographyManager.encryptData(token, this)
            cryptographyManager.persistCiphertextWrapperToSharedPreferences(
                encryptedServerTokenWrapper,
                context,
                Constants.SHARED_PREFERENCES_FILENAME,
                Context.MODE_PRIVATE,
                Constants.CIPHERTEXT_WRAPPER_KEY
            )
            onSuccessCallback?.invoke()
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
        val isPasswordLessThanSixChars = checkIfThePasswordIsLessThanSixCharacterUseCase(password)
        if (isPasswordLessThanSixChars) {
            passwordValidationErrorMessage =
                context.getString(R.string.password_can_not_be_less_than_six_chars_error_message)
            return false
        }
        passwordValidationErrorMessage = null
        return true
    }
}
package com.example.biometricauthdemo

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.biometricauthdemo.Constants.CIPHERTEXT_WRAPPER_KEY
import com.example.biometricauthdemo.Constants.SHARED_PREFERENCES_FILENAME
import com.example.biometricauthdemo.screen.login.screen.LoginScreen
import com.example.biometricauthdemo.screen.signup.screen.SignupScreen
import com.example.biometricauthdemo.ui.theme.BiometricAuthDemoTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val cryptographyManager = CryptographyManager()
    private lateinit var biometricPrompt: BiometricPrompt

    private var canAuthenticate: Int? = null


    private val ciphertextWrapper
        get() = cryptographyManager.getCiphertextWrapperFromSharedPreferences(
            applicationContext,
            SHARED_PREFERENCES_FILENAME,
            Context.MODE_PRIVATE,
            CIPHERTEXT_WRAPPER_KEY
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            canAuthenticate = BiometricManager.from(this).canAuthenticate()
            BiometricAuthDemoTheme {
                val navController = rememberNavController()
                CompositionLocalProvider(LocalContext provides this) {
                    NavHost(navController = navController, startDestination = LoginScreen) {
                        composable<LoginScreen> {
                            LoginScreen(
                                showLoginWithBiometricsButton = canAuthenticate != BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE,
                                onSignupButtonClick = {
                                    navController.navigate(SignupScreen)
                                }, onLoginWithBiometricsButtonClick = {
                                    if (ciphertextWrapper != null) {
                                        showBiometricPromptToLogin()
                                    } else {
                                        Toast.makeText(
                                            this@MainActivity,
                                            getString(R.string.login_with_credentials_first_error_message),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                })
                        }

                        composable<SignupScreen> {
                            SignupScreen(onBackButtonClick = navController::navigateUp)
                        }
                    }
                }
            }
        }
    }

    private fun showBiometricPromptToLogin() {
        ciphertextWrapper?.let { textWrapper ->
            val cipher = cryptographyManager.getInitializedCipherForDecryption(
                textWrapper.initializationVector
            )
            biometricPrompt =
                BiometricPromptUtils.createBiometricPrompt(this) { _ ->
                    loginWithBiometrics()
                }
            val promptInfo = BiometricPromptUtils.createPromptInfo(this)
            biometricPrompt.authenticate(promptInfo, BiometricPrompt.CryptoObject(cipher))
        }
    }

    private fun loginWithBiometrics() {
        Toast.makeText(
            this,
            getString(R.string.you_have_logged_in_successfully),
            Toast.LENGTH_SHORT
        ).show()
    }
}

@Serializable
object LoginScreen

@Serializable
object SignupScreen

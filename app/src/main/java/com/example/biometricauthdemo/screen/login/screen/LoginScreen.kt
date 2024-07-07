package com.example.biometricauthdemo.screen.login.screen

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.biometricauthdemo.ErrorDialog
import com.example.biometricauthdemo.Resource
import com.example.biometricauthdemo.screen.login.components.LoginContent
import com.example.biometricauthdemo.screen.login.viewmodel.LoginScreenViewModel


@Composable
fun LoginScreen(
    showLoginWithBiometricsButton: Boolean,
    modifier: Modifier = Modifier,
    onSignupButtonClick: () -> Unit = {},
    onLoginWithBiometricsButtonClick: () -> Unit = {},
) {

    val viewModel = hiltViewModel<LoginScreenViewModel>()

    val loginResource = viewModel.loginResponse.observeAsState()

    val isLoading = loginResource.value is Resource.Loading

    val showErrorDialog = viewModel.showErrorDialog

    val context = LocalContext.current

    var loginWithBiometricsButton by rememberSaveable {
        mutableStateOf(showLoginWithBiometricsButton)
    }

    val biometricSettingsLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            viewModel.token?.let {
                viewModel.showBiometricPromptForEncryption(context, it) {
                    loginWithBiometricsButton = true
                }
            }
        }

    LaunchedEffect(Unit) {
        viewModel.navigateUserToSettingsToEnrollBiometricAuthentication.collect { navigateToSettings ->
            if (navigateToSettings) {
                val action = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    Settings.ACTION_BIOMETRIC_ENROLL
                } else {
                    Settings.ACTION_SETTINGS
                }
                val intent = Intent(action)
                intent.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP)
                biometricSettingsLauncher.launch(intent)
            }
        }
    }

    Box(modifier = modifier.fillMaxSize()) {

        LoginContent(
            email = viewModel.email,
            password = viewModel.password,

            emailFieldErrorMessage = viewModel.emailValidationErrorMessage,
            passwordFieldErrorMessage = viewModel.passwordValidationErrorMessage,
            onSignupButtonClick = onSignupButtonClick,
            onLoginWithBiometricsButtonClick = onLoginWithBiometricsButtonClick,
            onLoginButtonClick = {
                if (viewModel.validateEmail(context) && viewModel.validatePassword(context)) {
                    viewModel.login(context)
                }
            },
            onEmailValueChanged = {
                viewModel.email = it
            },
            onPasswordValueChanged = {
                viewModel.password = it
            },
            showProgressBar = isLoading,
            showLoginWithBiometricsButton = showLoginWithBiometricsButton
        )


        AnimatedVisibility(
            visible = showErrorDialog,
            enter = scaleIn(),
            exit = scaleOut(),
            modifier = Modifier.align(
                Alignment.Center
            )
        ) {
            ErrorDialog(message = loginResource.value?.error?.asString(context)) {
                viewModel.showErrorDialog = false
            }
        }
    }

}
package com.example.biometricauthdemo.screen.signup.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.biometricauthdemo.ErrorDialog
import com.example.biometricauthdemo.Resource
import com.example.biometricauthdemo.screen.signup.components.SignupContent
import com.example.biometricauthdemo.screen.signup.viewmodel.SignupScreenViewModel

@Composable
fun SignupScreen(
    modifier: Modifier = Modifier,
    onBackButtonClick: () -> Unit = {}
) {
    val viewModel = hiltViewModel<SignupScreenViewModel>()

    val signupResource = viewModel.signupResponse.observeAsState()

    val isLoading = signupResource.value is Resource.Loading

    val showErrorDialog = viewModel.showErrorDialog

    val context = LocalContext.current

    Box(modifier = modifier.fillMaxSize()) {

        SignupContent(
            email = viewModel.email,
            password = viewModel.password,
            onEmailValueChanged = {
                viewModel.email = it
            },
            onPasswordValueChanged = {
                viewModel.password = it
            },
            onSignupButtonClick = {
                if (viewModel.validateEmail(context) && viewModel.validatePassword(context)) {
                    viewModel.signup()
                }
            },
            emailFieldErrorMessage = viewModel.emailValidationErrorMessage,
            passwordFieldErrorMessage = viewModel.passwordValidationErrorMessage,
            onBackButtonClick = onBackButtonClick,
            showProgressBar = isLoading,
        )

        AnimatedVisibility(
            visible = showErrorDialog,
            enter = scaleIn(),
            exit = scaleOut(),
            modifier = Modifier.align(
                Alignment.Center
            )
        ) {
            ErrorDialog(message = signupResource.value?.error?.asString(context)) {
                viewModel.showErrorDialog = false
            }
        }


    }
}
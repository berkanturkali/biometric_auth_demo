package com.example.biometricauthdemo.screen.login.components

import androidx.biometric.BiometricManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.biometricauthdemo.R
import com.example.biometricauthdemo.ui.theme.BiometricAuthDemoTheme
import com.example.biometricauthdemo.ui.theme.PrimaryTextColor

@Composable
fun LoginContent(
    email: String,
    password: String,
    showProgressBar: Boolean,
    showLoginWithBiometricsButton: Boolean,
    emailFieldErrorMessage: String?,
    passwordFieldErrorMessage: String?,
    modifier: Modifier = Modifier,
    onLoginButtonClick: () -> Unit = {},
    onLoginWithBiometricsButtonClick: () -> Unit = {},
    onSignupButtonClick: () -> Unit = {},
    onEmailValueChanged: (String) -> Unit = {},
    onPasswordValueChanged: (String) -> Unit = {},
) {

    Surface(modifier = modifier.fillMaxSize(), color = MaterialTheme.colorScheme.primary) {
        Box(modifier = Modifier.fillMaxSize()) {

            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.login_signup_top_banner),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillBounds
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 16.dp, bottom = 64.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.welcome),
                        style = MaterialTheme.typography.displaySmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 26.sp
                        ),
                        color = PrimaryTextColor,
                    )
                    Text(
                        text = stringResource(id = R.string.it_is_good_to_see_you_again),
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = PrimaryTextColor,
                        textAlign = TextAlign.End
                    )
                }
            }

            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 64.dp),
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {

                    val paddingModifier = Modifier.padding(horizontal = 12.dp)

                    Spacer(modifier = Modifier.height(12.dp))

                    EmailInput(
                        email = email,
                        showTrailingIcon = false,
                        modifier = paddingModifier,
                        errorMessage = emailFieldErrorMessage,
                        onValueChanged = onEmailValueChanged
                    )

                    PasswordInput(
                        password = password,
                        modifier = paddingModifier,
                        errorMessage = passwordFieldErrorMessage,
                        onValueChanged = onPasswordValueChanged
                    )

                    if (showLoginWithBiometricsButton) {
                        Text(
                            text = stringResource(id = R.string.login_with_biometrics),
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                            textDecoration = TextDecoration.Underline,
                            modifier = Modifier
                                .align(Alignment.End)
                                .then(paddingModifier)
                                .clickable {
                                    onLoginWithBiometricsButtonClick()
                                }
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Box(modifier = paddingModifier) {

                        LoginButton(onButtonClick = onLoginButtonClick, showText = !showProgressBar)

                        if (showProgressBar) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(24.dp),
                                color = MaterialTheme.colorScheme.onPrimary,
                                strokeWidth = 2.dp
                            )
                        }
                    }


                    Spacer(modifier = Modifier.height(12.dp))

                }

                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

                    Or(modifier = Modifier.padding(horizontal = 32.dp))

                    SignupButton(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        onButtonClick = onSignupButtonClick
                    )
                }

            }


        }
    }
}


@Preview(showBackground = true)
@Composable
private fun LoginContentPrev() {
    BiometricAuthDemoTheme {
        LoginContent(
            emailFieldErrorMessage = null,
            passwordFieldErrorMessage = null,
            email = "",
            password = "",
            showProgressBar = false,
            showLoginWithBiometricsButton = true
        )
    }
}
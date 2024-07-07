package com.example.biometricauthdemo.screen.signup.components

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.biometricauthdemo.R
import com.example.biometricauthdemo.screen.login.components.EmailInput
import com.example.biometricauthdemo.screen.login.components.PasswordInput
import com.example.biometricauthdemo.ui.theme.BiometricAuthDemoTheme

@Composable
fun SignupContent(
    email: String,
    password: String,
    showProgressBar: Boolean,
    emailFieldErrorMessage: String?,
    passwordFieldErrorMessage: String?,
    modifier: Modifier = Modifier,
    onSignupButtonClick: () -> Unit = {},
    onBackButtonClick: () -> Unit = {},
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
                Card(
                    shape = CircleShape,
                    elevation = CardDefaults.cardElevation(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.onPrimary,
                        contentColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier
                        .padding(16.dp)
                        .size(32.dp)
                        .clickable {
                            onBackButtonClick()
                        }
                ) {

                    IconButton(onClick = { onBackButtonClick() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(6.dp)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 64.dp),
                verticalArrangement = Arrangement.Center
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
                    Spacer(modifier = Modifier.height(12.dp))

                    val paddingModifier = Modifier.padding(horizontal = 12.dp)

                    EmailInput(
                        email = email,
                        showTrailingIcon = email.isNotEmpty(),
                        modifier = paddingModifier,
                        onValueChanged = {
                            onEmailValueChanged(it)
                        },
                        errorMessage = emailFieldErrorMessage
                    )

                    PasswordInput(
                        password = password,
                        modifier = paddingModifier,
                        onValueChanged = {
                            onPasswordValueChanged(it)
                        },
                        errorMessage = passwordFieldErrorMessage
                    )

                    Box(
                        modifier = paddingModifier
                    ) {
                        SignupButton(
                            onButtonClick = onSignupButtonClick,
                            showText = !showProgressBar,
                        )

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
            }
        }
    }
}

@Preview
@Composable
private fun SignupContentPrev() {
    BiometricAuthDemoTheme {
        SignupContent(
            email = "",
            password = "",
            emailFieldErrorMessage = null,
            passwordFieldErrorMessage = null,
            showProgressBar = false,
        )
    }
}

package com.example.biometricauthdemo.screen.login.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.biometricauthdemo.R
import com.example.biometricauthdemo.ui.theme.BiometricAuthDemoTheme
import com.example.biometricauthdemo.ui.theme.DividerOrBorderColor
import com.example.biometricauthdemo.ui.theme.PrimaryHintColor
import com.example.biometricauthdemo.ui.theme.PrimaryTextColor

@Composable
fun EmailInput(
    email: String,
    errorMessage: String?,
    showTrailingIcon: Boolean,
    modifier: Modifier = Modifier,
    onValueChanged: (String) -> Unit = {},
    onTrailingIconClick: () -> Unit = {},
) {

    val isError = errorMessage != null

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(),
        value = email,
        onValueChange = {
            onValueChanged(it)
        },
        label = {
            Text(
                text = stringResource(id = R.string.email),
                color = PrimaryHintColor,
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold)
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_email),
                contentDescription = null,
                tint = PrimaryHintColor
            )
        },
        trailingIcon = {
            AnimatedVisibility(visible = showTrailingIcon) {
                IconButton(onClick = onTrailingIconClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_clear),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        },
        singleLine = true,
        supportingText = {
            if (isError) {
                Text(
                    text = errorMessage!!,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        isError = isError,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = DividerOrBorderColor,
            errorBorderColor = MaterialTheme.colorScheme.error,
            cursorColor = PrimaryTextColor,
            focusedBorderColor = MaterialTheme.colorScheme.primary
        ),
        shape = RoundedCornerShape(6.dp),
    )

}

@Preview
@Composable
private fun EmailInputPrev() {
    BiometricAuthDemoTheme {
        EmailInput(email = "", showTrailingIcon = true, errorMessage = null)
    }
}
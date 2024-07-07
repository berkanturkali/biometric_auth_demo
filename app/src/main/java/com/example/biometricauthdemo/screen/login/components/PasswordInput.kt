package com.example.biometricauthdemo.screen.login.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.biometricauthdemo.R
import com.example.biometricauthdemo.ui.theme.DividerOrBorderColor
import com.example.biometricauthdemo.ui.theme.PrimaryHintColor
import com.example.biometricauthdemo.ui.theme.PrimaryTextColor

@Composable
fun PasswordInput(
    password: String,
    errorMessage: String?,
    modifier: Modifier = Modifier,
    onValueChanged: (String) -> Unit = {},
) {

    val isError = errorMessage != null

    var passwordVisible by rememberSaveable {
        mutableStateOf(false)
    }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(),
        value = password,
        onValueChange = {
            onValueChanged(it)
        },
        label = {
            Text(
                text = stringResource(id = R.string.password),
                color = PrimaryHintColor,
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold)
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_key),
                contentDescription = null,
                tint = PrimaryHintColor
            )
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
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = DividerOrBorderColor,
            errorBorderColor = MaterialTheme.colorScheme.error,
            cursorColor = PrimaryTextColor,
            focusedBorderColor = MaterialTheme.colorScheme.primary
        ),
        shape = RoundedCornerShape(6.dp),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val icon = if (passwordVisible)
                R.drawable.ic_visibility
            else R.drawable.ic_visibility_off

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(
                    painter = painterResource(id = icon),
                    null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
    )
}
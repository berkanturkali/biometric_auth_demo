package com.example.biometricauthdemo.screen.login.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.biometricauthdemo.R
import com.example.biometricauthdemo.ui.theme.BiometricAuthDemoTheme

@Composable
fun SignupButton(
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit = {},
) {
    Button(
        onClick = onButtonClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Text(
            text = stringResource(id = R.string.signup),
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview
@Composable
private fun SignupButtonPrev() {
    BiometricAuthDemoTheme {
        SignupButton()
    }
}
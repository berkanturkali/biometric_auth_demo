package com.example.biometricauthdemo.screen.signup.components

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.biometricauthdemo.R
import com.example.biometricauthdemo.ui.theme.BiometricAuthDemoTheme

@Composable
fun SignupButton(
    showText: Boolean,
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit = {},
) {

    val buttonAlpha by animateFloatAsState(
        targetValue = if (showText) 1f else 0.5f,
        animationSpec = tween(
            300,
            delayMillis = 0,
            easing = EaseInOut
        )
    )
    Button(
        onClick = {
            onButtonClick()
        },
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = buttonAlpha),
        ),
        enabled = showText
    ) {
        if (showText) {
            Text(
                text = stringResource(id = R.string.signup),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Preview
@Composable
private fun SignupButtonPrev() {
    BiometricAuthDemoTheme {
        SignupButton(showText = true)
    }
}
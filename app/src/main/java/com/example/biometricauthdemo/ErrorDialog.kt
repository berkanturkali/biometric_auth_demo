package com.example.biometricauthdemo

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.biometricauthdemo.ui.theme.BiometricAuthDemoTheme

@Composable
fun ErrorDialog(
    message: String?,
    modifier: Modifier = Modifier,
    onOkayButtonClick: () -> Unit = {},
) {

    Dialog(
        onDismissRequest = {},
    ) {
        Surface(
            modifier = modifier,
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.primary,
            border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f))
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 24.dp, horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    text = message ?: stringResource(id = R.string.unknown_error_message),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = stringResource(id = R.string.okay),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.onPrimary,
                            shape = RoundedCornerShape(7.dp)
                        )
                        .padding(horizontal = 32.dp, vertical = 6.dp)
                        .clickable {
                            onOkayButtonClick()
                        },
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview
@Composable
private fun ErrorDialogPrev() {
    BiometricAuthDemoTheme {
        ErrorDialog(message = stringResource(id = R.string.unknown_error_message))
    }
}
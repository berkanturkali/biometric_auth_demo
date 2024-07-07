package com.example.biometricauthdemo.screen.login.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.biometricauthdemo.R
import com.example.biometricauthdemo.ui.theme.BiometricAuthDemoTheme
import com.example.biometricauthdemo.ui.theme.DividerOrBorderColor

@Composable
fun Or(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {

        Divider(thickness = 0.5.dp, color = DividerOrBorderColor, modifier = Modifier.weight(0.4f))

        Text(
            text = stringResource(id = R.string.or),
            style = MaterialTheme.typography.labelLarge,
            color = DividerOrBorderColor
        )


        Divider(thickness = 0.5.dp, color = DividerOrBorderColor, modifier = Modifier.weight(0.4f))


    }
}

@Preview
@Composable
private fun OrPrev() {
    BiometricAuthDemoTheme {
        Or()
    }
}
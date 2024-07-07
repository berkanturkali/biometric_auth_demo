package com.example.biometricauthdemo.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.biometricauthdemo.R

// Set of Material typography styles to start with

val Nunito = FontFamily(
    Font(resId = R.font.nunito_black, weight = FontWeight.Black, style = FontStyle.Normal),
    Font(resId = R.font.nunito_black_italic, weight = FontWeight.Black, style = FontStyle.Italic),
    Font(resId = R.font.nunito_bold, weight = FontWeight.Bold, style = FontStyle.Normal),
    Font(resId = R.font.nunito_bold_italic, weight = FontWeight.Bold, style = FontStyle.Italic),
    Font(resId = R.font.nunito_extra_bold, weight = FontWeight.ExtraBold, style = FontStyle.Normal),
    Font(
        resId = R.font.nunito_extra_bold_italic,
        weight = FontWeight.ExtraBold,
        style = FontStyle.Italic
    ),
    Font(
        resId = R.font.nunito_extra_light,
        weight = FontWeight.ExtraLight,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.nunito_extra_light_italic,
        weight = FontWeight.ExtraLight,
        style = FontStyle.Italic
    ),
    Font(resId = R.font.nunito_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
    Font(resId = R.font.nunito_light, weight = FontWeight.Light, style = FontStyle.Normal),
    Font(resId = R.font.nunito_light_italic, weight = FontWeight.Light, style = FontStyle.Italic),
    Font(resId = R.font.nunito_medium, weight = FontWeight.Medium, style = FontStyle.Normal),
    Font(resId = R.font.nunito_medium_italic, weight = FontWeight.Medium, style = FontStyle.Italic),
    Font(resId = R.font.nunito_regular, weight = FontWeight.Normal, style = FontStyle.Normal),
    Font(resId = R.font.nunito_semi_bold, weight = FontWeight.SemiBold, style = FontStyle.Normal),
    Font(
        resId = R.font.nunito_semi_bold_italic,
        weight = FontWeight.SemiBold,
        style = FontStyle.Italic
    ),
)

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 36.sp
    ),
    displayMedium = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp
    ),
    displaySmall = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.Black,
        fontSize = 28.sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.Black,
        fontSize = 24.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.Black,
        fontSize = 22.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.Black,
        fontSize = 20.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.Light,
        fontSize = 8.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.Light,
        fontSize = 7.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.ExtraLight,
        fontSize = 7.sp,
    ),
)
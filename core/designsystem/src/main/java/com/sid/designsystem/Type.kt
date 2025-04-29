package com.sid.designsystem

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

val AppTypography = Typography()

val dotoFamily = FontFamily(
    Font(R.font.doto_light, FontWeight.Light),
    Font(R.font.doto_regular, FontWeight.Normal),
    Font(R.font.doto_black, FontWeight.Black),
    Font(R.font.doto_medium, FontWeight.Medium),
    Font(R.font.doto_bold, FontWeight.Bold)
)

val pacificoFamily = FontFamily(
    Font(R.font.pacifico_regular, FontWeight.Light),
)
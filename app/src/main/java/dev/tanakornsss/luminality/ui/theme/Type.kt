package dev.tanakornsss.luminality.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import dev.tanakornsss.luminality.R

// Set of Material typography styles to start with

val Sriracha = FontFamily(
    Font(R.font.sriracha_regular, FontWeight.Normal)
)

val Typography = Typography().run {
    copy(
        displayLarge = displayLarge.copy(fontFamily = Sriracha),
        displayMedium = displayMedium.copy(fontFamily = Sriracha),
        displaySmall = displaySmall.copy(fontFamily = Sriracha),
        headlineLarge = headlineLarge.copy(fontFamily = Sriracha),
        headlineMedium = headlineMedium.copy(fontFamily = Sriracha),
        headlineSmall = headlineSmall.copy(fontFamily = Sriracha),
        titleLarge = titleLarge.copy(fontFamily = Sriracha),
        titleMedium = titleMedium.copy(fontFamily = Sriracha),
        titleSmall = titleSmall.copy(fontFamily = Sriracha),
        bodyLarge = bodyLarge.copy(fontFamily = Sriracha),
        bodyMedium = bodyMedium.copy(fontFamily = Sriracha),
        bodySmall = bodySmall.copy(fontFamily = Sriracha),
        labelLarge = labelLarge.copy(fontFamily = Sriracha),
        labelMedium = labelMedium.copy(fontFamily = Sriracha),
        labelSmall = labelSmall.copy(fontFamily = Sriracha)
    )
}

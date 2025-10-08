package dev.tanakornsss.luminality

import androidx.compose.runtime.Composable
import dev.tanakornsss.luminality.ui.screen.LandingScreen
import dev.tanakornsss.luminality.ui.theme.LuminalityTheme

@Composable
fun LuminalityApp() {
    LuminalityTheme {
        LandingScreen()
    }
}
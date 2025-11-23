package dev.tanakornsss.luminality.setting

import dev.tanakornsss.luminality.ui.theme.ThemeState

data class Setting(
    val enableNotifications: Boolean = false,
    val themeState: ThemeState = ThemeState.DEFAULT
)

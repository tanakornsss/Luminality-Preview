package dev.tanakornsss.luminality.setting

import dev.tanakornsss.luminality.ui.theme.ThemeState
import kotlinx.coroutines.flow.Flow

interface SettingRepository {
    suspend fun updateThemeState(themeState: ThemeState)
    fun readThemeState() : Flow<ThemeState>
}
package dev.tanakornsss.luminality.setting

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dev.tanakornsss.luminality.ui.theme.ThemeState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.settingDatastore by preferencesDataStore(name = "setting_prefs")

class SettingRepository(private val context: Context) {

    object SettingPrefs {
        val THEME_STATE = stringPreferencesKey("theme_state")
        val TELEMETRY_STATE = booleanPreferencesKey("telemetry_state")
    }

    suspend fun updateThemeState(themeState: ThemeState) {
        context.settingDatastore.edit { prefs ->
            prefs[SettingPrefs.THEME_STATE] = themeState.name
        }
    }

    fun readThemeState() : Flow<ThemeState> {
        return context.settingDatastore.data.map { prefs ->
            ThemeState.valueOf(prefs[SettingPrefs.THEME_STATE] ?: ThemeState.DEFAULT.name)
        }
    }

    suspend fun updateTelemetryState(state: Boolean) {
        context.settingDatastore.edit { prefs ->
            prefs[SettingPrefs.TELEMETRY_STATE] = state
        }
    }

    fun readTelemetryState() : Flow<Boolean> {
        return context.settingDatastore.data.map { prefs ->
            prefs[SettingPrefs.TELEMETRY_STATE] ?: false
        }
    }
}

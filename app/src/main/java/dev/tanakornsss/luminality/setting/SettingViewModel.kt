package dev.tanakornsss.luminality.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tanakornsss.luminality.ui.theme.ThemeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingViewModel(private val settingRepository: SettingRepository) : ViewModel() {
    private val _setting = MutableStateFlow(Setting())
    val setting = _setting.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                settingRepository.readThemeState(),
                settingRepository.readTelemetryState()
            ) { theme, telemetry ->
                Setting(
                    telemetryState = telemetry,
                    themeState = theme
                )
            }.collect { setting ->
                _setting.value = setting
            }
        }
    }

    val settingSection = listOf(
        SettingsSection(
            title = "Analytics",
            items = listOf(
                SettingItem.Toggle(
                    title = "Telemetry",
                    desc = "Enable telemetry",
                    value = _setting.value.telemetryState,
                    onChange = { updateTelemetry(it) },
                )
            )
        )
    )

    fun updateTheme(themeState: ThemeState) {
        viewModelScope.launch {
            settingRepository.updateThemeState(themeState)
        }
    }

    fun updateTelemetry(state: Boolean) {
        viewModelScope.launch {
            settingRepository.updateTelemetryState(state)
        }
    }

    fun updateNotificationPermission(state: Boolean) {
        viewModelScope.launch {
            _setting.update {
                it.copy(
                    enableNotifications = state
                )
            }
        }
    }
}

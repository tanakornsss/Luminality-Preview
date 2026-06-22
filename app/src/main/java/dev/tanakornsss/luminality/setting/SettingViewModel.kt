package dev.tanakornsss.luminality.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tanakornsss.luminality.ui.theme.ThemeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingViewModel(private val settingRepository: SettingRepositoryImpl) : ViewModel() {
    private val _setting = MutableStateFlow(Setting())
    val setting = _setting.asStateFlow()

    // Placeholder WIP
    init {
        viewModelScope.launch {  }
    }

    fun updateTheme(themeState: ThemeState) {
        viewModelScope.launch {
            settingRepository.updateThemeState(themeState)
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

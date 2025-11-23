package dev.tanakornsss.luminality.setting

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tanakornsss.luminality.ui.theme.ThemeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingViewModel(context: Context) : ViewModel() {
    private val repository = SettingRepository(context)

    private val _setting = MutableStateFlow(Setting())
    val setting = _setting.asStateFlow()

    init {
        viewModelScope.launch {
            repository.readThemeState().collect { theme ->
                _setting.update {
                    it.copy(themeState = theme)
                }
            }
        }
    }

    fun updateTheme(themeState: ThemeState) {
        viewModelScope.launch {
            repository.updateThemeState(themeState)
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

package dev.tanakornsss.luminality.setting

import androidx.annotation.StringRes
import dev.tanakornsss.luminality.R

enum class SettingItems(@StringRes val label: Int) {
    NOTIFICATIONS(label = R.string.enable_notifications),
    THEME_PREFS(label = R.string.theme_prefs),
    TELEMETRY(label = R.string.analytics_toggle)
}

sealed class SettingItem {
    data class Toggle(
        val title: String,
        val desc: String,
        val value: Boolean,
        val onChange: (Boolean) -> Unit
    ) : SettingItem()

    data class Navigation(
        val title: String,
        val desc: String,
        val onClick: () -> Unit
    ) : SettingItem()
}

data class SettingsSection(
    val title: String,
    val items: List<SettingItem>
)

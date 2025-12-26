package dev.tanakornsss.luminality.setting

import androidx.annotation.StringRes
import dev.tanakornsss.luminality.R

enum class SettingItems(@StringRes val label: Int) {
    NOTIFICATIONS(label = R.string.enable_notifications),
    THEME_PREFS(label = R.string.theme_prefs)
}

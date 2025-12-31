package dev.tanakornsss.luminality.ui.component.setting

import androidx.compose.runtime.Composable
import dev.tanakornsss.luminality.setting.SettingItem

@Composable
fun SettingRouter(item: SettingItem) {
    when (item) {
        is SettingItem.Navigation -> SettingNavigate(item)
        is SettingItem.Toggle -> SettingToggle(item)
        is SettingItem.ViewOnly -> DisclaimerRow(item)
    }
}

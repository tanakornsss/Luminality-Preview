package dev.tanakornsss.luminality.ui.component.setting

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import dev.tanakornsss.luminality.setting.SettingsSection

@Composable
fun SettingSection(section: SettingsSection) {
    Text(section.title)

    section.items.forEach { items ->
        SettingRouter(items)
    }
}
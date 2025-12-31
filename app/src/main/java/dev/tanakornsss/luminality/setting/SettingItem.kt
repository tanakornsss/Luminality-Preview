package dev.tanakornsss.luminality.setting

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

    data class ViewOnly(
        val title: String,
        val desc: String,
    ) : SettingItem()
}

data class SettingsSection(
    val title: String,
    val items: List<SettingItem>
)

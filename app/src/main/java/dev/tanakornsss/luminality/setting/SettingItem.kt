package dev.tanakornsss.luminality.setting

enum class SettingItems(
    val label: String,
    val onClick: () -> Unit,
    val state: Boolean
) {
    NOTIFICATIONS(
        label = "Enable notifications",
        onClick = {

        },
        state = true // Placeholder, will be in a separate ViewModel
    ),
    DARK_MODE(
        label = "Enable dark mode",
        onClick = {

        },
        state = true // Placeholder, will be in a separate ViewModel
    ),
}

package dev.tanakornsss.luminality.ui.component.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import dev.tanakornsss.luminality.setting.SettingsSection
import dev.tanakornsss.luminality.ui.APP_SIDE_PADDING
import dev.tanakornsss.luminality.ui.CATEGORY_TEXT_OPACITY

@Composable
fun SettingScrollList(section: SettingsSection) {
    Column(modifier = Modifier.padding(bottom = APP_SIDE_PADDING)) {
        Text(
            text = section.title,
            modifier = Modifier
                .padding(horizontal = APP_SIDE_PADDING)
                .alpha(CATEGORY_TEXT_OPACITY)
        )

        section.items.forEach { items ->
            SettingRouter(items)
        }
    }
}
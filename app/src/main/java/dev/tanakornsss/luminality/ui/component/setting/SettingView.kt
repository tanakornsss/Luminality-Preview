package dev.tanakornsss.luminality.ui.component.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import dev.tanakornsss.luminality.setting.SettingItem
import dev.tanakornsss.luminality.ui.APP_SIDE_PADDING
import dev.tanakornsss.luminality.ui.SETTING_SELECTION_PADDING
import dev.tanakornsss.luminality.ui.SUBTITLE_TEXT_OPACITY

@Composable
private fun BaseSettingComponent(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    trailingContent: @Composable () -> Unit = { }
) {
    Column(
        modifier = modifier
            .padding(vertical = SETTING_SELECTION_PADDING)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = APP_SIDE_PADDING),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge

                )
                Text(
                    text = subtitle,
                    modifier = Modifier.alpha(SUBTITLE_TEXT_OPACITY)
                )
            }
            trailingContent()
        }
    }
}


@Composable
fun DisclaimerRow(item: SettingItem.ViewOnly) {
    BaseSettingComponent(
        title = item.title,
        subtitle = item.desc,
    )
}

@Composable
fun SettingToggle(item: SettingItem.Toggle) {
    BaseSettingComponent(
        title = item.title,
        subtitle = item.desc,
        trailingContent = {
            Switch(
                checked = item.value,
                onCheckedChange = { item.onChange(it) }
            )
        }
    )
}

@Composable
fun SettingNavigate(item: SettingItem.Navigation) {
    BaseSettingComponent(
        title = item.title,
        subtitle = item.desc,
        modifier = Modifier.clickable(onClick = { item.onClick() })
    )
}

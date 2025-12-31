package dev.tanakornsss.luminality.ui.component.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import dev.tanakornsss.luminality.ui.APP_SIDE_PADDING
import dev.tanakornsss.luminality.ui.SETTING_SELECTION_HEIGHT
import dev.tanakornsss.luminality.ui.SUBTITLE_TEXT_OPACITY
import dev.tanakornsss.luminality.ui.theme.LuminalityTheme

@Composable
private fun BaseSettingComponent(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    trailingContent: @Composable () -> Unit = { }
) {
    Column(
        modifier = modifier
            .height(SETTING_SELECTION_HEIGHT)
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
fun SettingToggle(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    BaseSettingComponent(
        title = title,
        subtitle = subtitle,
        trailingContent = {
            Switch(
                checked = checked,
                onCheckedChange = { onCheckedChange(it) }
            )
        }
    )
}

@Composable
fun SettingInteract(
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    BaseSettingComponent(
        title = title,
        subtitle = subtitle,
        modifier = Modifier.clickable(onClick = { onClick() })
    )
}

@Preview(showBackground = true)
@Composable
fun SettingTogglePreview() {
    LuminalityTheme {
        SettingToggle(
            title = "Title",
            subtitle = "Subtitle",
            checked = false,
            onCheckedChange = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingInteractPreview() {
    LuminalityTheme {
        SettingInteract(
            title = "Title",
            subtitle = "Subtitle",
            onClick = { }
        )
    }
}

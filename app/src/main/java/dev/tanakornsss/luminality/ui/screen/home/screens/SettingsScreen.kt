package dev.tanakornsss.luminality.ui.screen.home.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tanakornsss.luminality.setting.SettingItems
import dev.tanakornsss.luminality.ui.component.CustomListItem

@Composable
fun SettingsScreen(innerPadding: PaddingValues) {
    Column(modifier = Modifier
        .padding(innerPadding)
        .padding(horizontal = 16.dp)
        .fillMaxSize()
    ) {
        var enableNotification by remember { mutableStateOf(false) }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(SettingItems.entries.toList()) { settings ->
                when (settings) {
                    SettingItems.NOTIFICATIONS -> {
                        CustomListItem(label = settings.label) {
                            Switch(
                                checked = enableNotification,
                                onCheckedChange = {
                                    enableNotification = it
                                },
                            )
                        }
                    }
                    SettingItems.DARK_MODE -> {
                        CustomListItem(
                            label = settings.label,
                            modifier = Modifier
                                .clickable(
                                    onClick = {

                                    }
                                )
                        )
                    }
                }
            }
        }
    }
}

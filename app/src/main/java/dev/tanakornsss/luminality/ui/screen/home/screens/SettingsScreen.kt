package dev.tanakornsss.luminality.ui.screen.home.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
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
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(SettingItems.entries.toList()) { settings ->
                when (settings) {
                    SettingItems.NOTIFICATIONS -> {
                        CustomListItem(label = settings.label) {

                        }
                    }
                    SettingItems.DARK_MODE -> {
                        CustomListItem(label = settings.label) {

                        }
                    }
                }
            }
        }
    }
}

package dev.tanakornsss.luminality.ui.screen.home.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tanakornsss.luminality.setting.SettingItems

@Composable
fun SettingsScreen(innerPadding: PaddingValues) {
    Column(modifier = Modifier
        .padding(innerPadding)
        .padding(horizontal = 16.dp)
        .fillMaxSize()
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(SettingItems.entries.toList()) { settings ->
                SettingsRow(
                    checked = settings.state,
                    onCheckedChange = { settings.onClick },
                    label = settings.label
                )
            }
        }
    }
}

@Composable
private fun SettingsRow(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label)
        Switch(
            checked = checked,
            onCheckedChange = { onCheckedChange(it) },
        )
    }
}

package dev.tanakornsss.luminality.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dev.tanakornsss.luminality.R
import dev.tanakornsss.luminality.setting.SettingItems
import dev.tanakornsss.luminality.ui.component.CustomListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(onNavigateBack: () -> Unit) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { onNavigateBack() }
                        ) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                        }
                        Text(stringResource(R.string.settings))
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(SettingItems.entries.toList()) { settings ->
                    when (settings) {
                        SettingItems.NOTIFICATIONS -> {
                            CustomListItem(
                                label = settings.label,
                                modifier = Modifier.clickable(
                                    onClick = { }
                                ),
                            )
                        }
                        SettingItems.THEME_PREFS -> {
                            CustomListItem(
                                label = settings.label,
                                modifier = Modifier
                                    .clickable(
                                        onClick = { }
                                    )
                            )
                        }

                        SettingItems.CRASH -> {
                            CustomListItem(
                                label = settings.label,
                                modifier = Modifier
                                    .clickable(
                                        onClick = {
                                            throw RuntimeException("Test crash")
                                        }
                                    )
                            )
                        }
                    }
                }
            }
        }
    }
}
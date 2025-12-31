package dev.tanakornsss.luminality.ui.screen

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dev.tanakornsss.luminality.R
import dev.tanakornsss.luminality.setting.SettingItem
import dev.tanakornsss.luminality.setting.SettingViewModel
import dev.tanakornsss.luminality.setting.SettingsSection
import dev.tanakornsss.luminality.ui.component.setting.SettingScrollList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    onNavigateBack: () -> Unit,
    settingViewModel: SettingViewModel
) {
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
            val settingValue by settingViewModel.setting.collectAsState()
            val telemetryState = settingValue.telemetryState

            val settingSection = listOf(
                SettingsSection(
                    title = stringResource(R.string.analytics),
                    items = listOf(
                        SettingItem.Toggle(
                            title = stringResource(R.string.telemetry),
                            desc = stringResource(R.string.analytics_toggle),
                            value = telemetryState,
                            onChange = { settingViewModel.updateTelemetry(it) },
                        )
                    )
                ),
                SettingsSection(
                    title = stringResource(R.string.about),
                    items = listOf(
                        SettingItem.ViewOnly(
                            title = "Disclaimer",
                            desc = stringResource(R.string.advice)
                        )
                    )
                )
            )
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(settingSection) { section ->
                    SettingScrollList(section)
                }
            }
        }
    }
}

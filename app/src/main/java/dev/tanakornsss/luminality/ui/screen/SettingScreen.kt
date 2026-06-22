package dev.tanakornsss.luminality.ui.screen

import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dev.tanakornsss.luminality.BuildConfig
import dev.tanakornsss.luminality.R
import dev.tanakornsss.luminality.data.backup.BackupViewModel
import dev.tanakornsss.luminality.setting.SettingItem
import dev.tanakornsss.luminality.setting.SettingsSection
import dev.tanakornsss.luminality.ui.component.setting.SettingScrollList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    onNavigateBack: () -> Unit,
    backupViewModel: BackupViewModel
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
                        IconButton(onClick = { onNavigateBack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                        }
                        Text(
                            text = stringResource(R.string.settings),
                            style = MaterialTheme.typography.headlineLarge
                        )
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
            val exportLauncher = rememberLauncherForActivityResult(
                ActivityResultContracts.CreateDocument("application/json")
            ) { uri: Uri? ->
                uri?.let {
                    backupViewModel.exportToUri(it)
                }
            }

            val importLauncher = rememberLauncherForActivityResult(
                ActivityResultContracts.GetContent()
            ) { uri: Uri? ->
                uri?.let {
                    backupViewModel.importFromUri(it)
                }
            }

            val settingSection = listOf(
                SettingsSection(
                    title = "Backup",
                    items = listOf(
                        SettingItem.Navigation(
                            title = "Create Backup",
                            desc = "...",
                            onClick = {
                                exportLauncher.launch("LuminalityBackup.json")
                            }
                        ),
                        SettingItem.Navigation(
                            title = "Restore Backup",
                            desc = "...",
                            onClick = {
                                importLauncher.launch("application/json")
                            }
                        )
                    )
                ),
                SettingsSection(
                    title = stringResource(R.string.about),
                    items = listOf(
                        SettingItem.ViewOnly(
                            title = stringResource(R.string.disclaimer),
                            desc = stringResource(R.string.advice)
                        ),
                        SettingItem.ViewOnly(
                            title = stringResource(R.string.app_info),
                            desc = stringResource(
                                R.string.app_info_details,
                                BuildConfig.VERSION_NAME,
                                Build.VERSION.SDK_INT
                            )
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

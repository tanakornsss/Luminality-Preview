package dev.tanakornsss.luminality.ui.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Upload
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tanakornsss.luminality.data.backup.BackupViewModel
import dev.tanakornsss.luminality.data.journal.Journal
import dev.tanakornsss.luminality.data.journal.JournalViewModel
import dev.tanakornsss.luminality.ui.component.CustomAlertDialog
import java.time.Instant
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewJournalScreen(
    journalViewModel: JournalViewModel,
    backupViewModel: BackupViewModel
) {
    var textFieldValue by remember { mutableStateOf("") }
    var pendingDeleteJournal by remember { mutableStateOf<Journal?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    val streak by journalViewModel.streak.collectAsState()

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

    if (showDeleteDialog) DeleteAlertDialog(
        onDismiss = { showDeleteDialog = false },
        onConfirm = {
            pendingDeleteJournal?.let {
                journalViewModel.deleteEntry(it)
            }
            showDeleteDialog = false
        }
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("App name")
                        Row {
                            IconButton(
                                onClick = {
                                    exportLauncher.launch("LuminalityBackup.json")
                                }
                            ) {
                                Icon(Icons.Outlined.Upload, null)
                            }
                            IconButton(
                                onClick = { importLauncher.launch("application/json") }
                            ) {
                                Icon(Icons.Outlined.Download, null)
                            }
                            IconButton(onClick = { }) {
                                Icon(Icons.Outlined.Settings, null)
                            }
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("App logo here")
                Text("Streak: ${streak.currentStreak}")
            }
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 16.dp,
                        horizontal = 16.dp
                    )
                    .imePadding(),
                value = textFieldValue,
                onValueChange = {
                    textFieldValue = it
                },
                label = {
                    Text("What's good today?")
                },
                trailingIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Outlined.Add, null)
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomContent(
    journalViewModel: JournalViewModel,
    onDelete: (Journal) -> Unit
) {
    val journalNew by journalViewModel.entries.collectAsState()

    Spacer(Modifier.height(16.dp))
    Text(
        text = "Your journeys",
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(horizontal = 16.dp)
    )

    Spacer(modifier = Modifier.height(20.dp))

    val grouped = journalNew.groupBy { entries ->
        Instant.ofEpochMilli(entries.createdAt)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    }

    LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
        grouped.forEach { (date, entriesOfDay) ->
            item {
                Text(date.toString())
                Spacer(modifier = Modifier.height(12.dp))
            }
            items(entriesOfDay) { entries ->
                MessageCard(
                    message = entries.text,
                    onDelete = { onDelete(entries) },
                )
            }
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }

}

@Composable
private fun MessageCard(
    message: String,
    onDelete: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge
        )
        IconButton(onClick = { onDelete() }) {
            Icon(Icons.Outlined.Delete, null)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DeleteAlertDialog(onDismiss: () -> Unit, onConfirm: () -> Unit) {
    CustomAlertDialog(
        onDismissRequest = { onDismiss() },
        onConfirmation = { onConfirm() },
        dialogTitle = "Delete selected journal?",
        dialogText = "Your journal will be permanently deleted",
        dismissText = "Cancel",
        confirmText = "Delete",
    )
}


package dev.tanakornsss.luminality.ui.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Upload
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import dev.tanakornsss.luminality.data.backup.BackupViewModel
import dev.tanakornsss.luminality.data.journal.Journal
import dev.tanakornsss.luminality.data.journal.JournalViewModel
import dev.tanakornsss.luminality.ui.component.CustomAlertDialog
import dev.tanakornsss.luminality.ui.screen.journal.AddJournalScreen
import dev.tanakornsss.luminality.ui.screen.journal.JournalListScreen

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

    val pagerState = rememberPagerState(pageCount = { 2 })

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

    val focusManager = LocalFocusManager.current

    LaunchedEffect(pagerState.currentPage) {
        focusManager.clearFocus()
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
        HorizontalPager(state = pagerState) { page ->
            when (page) {
                0 -> AddJournalScreen(
                    innerPadding = innerPadding,
                    streak = streak,
                    textFieldValue = textFieldValue,
                    textFieldUpdate = {
                        textFieldValue = it
                    },
                    addEntry = {
                        journalViewModel.addEntry(textFieldValue)
                        textFieldValue = ""
                    }
                )
                1 -> JournalListScreen(
                    innerPadding = innerPadding,
                    journalViewModel = journalViewModel,
                    pendingDeleteJournal = {
                        pendingDeleteJournal = it
                    },
                    deleteDialogTrigger = {
                        showDeleteDialog = it
                    }
                )
            }
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

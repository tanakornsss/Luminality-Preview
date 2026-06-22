package dev.tanakornsss.luminality.ui.screen.intoduction

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import dev.tanakornsss.luminality.R
import dev.tanakornsss.luminality.data.journal.JournalViewModel
import dev.tanakornsss.luminality.data.model.Journal
import dev.tanakornsss.luminality.ui.component.CustomAlertDialog
import dev.tanakornsss.luminality.ui.screen.journal.AddJournalScreen
import dev.tanakornsss.luminality.ui.screen.journal.JournalListScreen
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun JournalScreen(
    journalViewModel: JournalViewModel,
    onNavigateSetting: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { 2 })
    val focusManager = LocalFocusManager.current
    LaunchedEffect(pagerState.currentPage) {
        focusManager.clearFocus()
    }

    var pendingDeleteJournal by remember { mutableStateOf<Journal?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    if (showDeleteDialog) DeleteAlertDialog(
        onDismiss = { showDeleteDialog = false },
        onConfirm = {
            pendingDeleteJournal?.let {
                journalViewModel.deleteEntry(it)
            }
            showDeleteDialog = false
        }
    )

//    val postNotificationPermission =
//        rememberPermissionState(Manifest.permission.POST_NOTIFICATIONS)
//
//    LaunchedEffect(true) {
//        if (!postNotificationPermission.status.isGranted) {
//            postNotificationPermission.launchPermissionRequest()
//        }
//    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.app_name),
                            style = MaterialTheme.typography.headlineLarge

                        )
                        Row {
                            IconButton(
                                onClick = {
                                    focusManager.clearFocus()
                                    onNavigateSetting()
                                }
                            ) {
                                Icon(Icons.Outlined.Settings, null)
                            }
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        val streak by journalViewModel.streak.collectAsState()
        val scope = rememberCoroutineScope()
        var textFieldValue by remember { mutableStateOf("") }
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
                        scope.launch {
                            pagerState.animateScrollToPage(1)
                        }
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
                    },
                    journalToEdit = { journal ->
                        journalViewModel.updateEntry(journal)
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
        dialogTitle = stringResource(R.string.delete_journal),
        dialogText = stringResource(R.string.delete_confirm),
        dismissText = stringResource(R.string.cancel),
        confirmText = stringResource(R.string.delete),
    )
}

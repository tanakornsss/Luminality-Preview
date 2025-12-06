package dev.tanakornsss.luminality.ui.screen

import android.Manifest
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import dev.tanakornsss.luminality.data.JournalViewModelNew
import dev.tanakornsss.luminality.notification.NotificationHandler
import dev.tanakornsss.luminality.notification.NotificationScheduler
import dev.tanakornsss.luminality.ui.component.CustomAlertDialog
import java.time.Instant
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun JournalScreen(
    journalViewModelNew: JournalViewModelNew,
    context: Context
) {
    var textFieldValue by remember { mutableStateOf("") }

    var showDeleteDialog by remember { mutableStateOf(false) }
    var pendingDeleteMessage by remember { mutableStateOf<String?>(null) }
    var pendingDeleteDate by remember { mutableStateOf<String?>(null) }

    val postNotificationPermission =
        rememberPermissionState(Manifest.permission.POST_NOTIFICATIONS)
    val notificationHandler = NotificationHandler(context)

    LaunchedEffect(true) {
        if (!postNotificationPermission.status.isGranted) {
            postNotificationPermission.launchPermissionRequest()
        }
    }

    if (showDeleteDialog) {
        DeleteAlertDialog(
            onDismiss = {
                showDeleteDialog = false
            },
            onConfirm = {
                showDeleteDialog = false
            }
        )
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .padding(horizontal = 16.dp)
            .fillMaxSize()
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = textFieldValue,
                        onValueChange = {
                            textFieldValue = it
                        },
                        label = {
                            Text("What's good today?")
                        },
                    )
                    Button(
                        onClick = {
                            journalViewModelNew.addEntry(textFieldValue)
                            textFieldValue = ""
                        },
                        enabled = textFieldValue.isNotBlank(),
                        modifier = Modifier.height(56.dp)
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = null)
                    }
                }
                Spacer(modifier = Modifier.padding(vertical = 16.dp))
                Button(onClick = {
                    notificationHandler.showNotification("Hello", "Hello") }) {
                    Text("Show notification")
                }
                Button(onClick = {
                    NotificationScheduler.scheduleDailyNotification(context)
                }) {
                    Text("Schedule notification")
                }
            }
            MessageSlider(
                journalViewModelNew = journalViewModelNew,
                onDelete = { }
            )
        }
    }
}

@Composable
private fun MessageSlider(
    journalViewModelNew: JournalViewModelNew,
    onDelete: () -> Unit
) {
    val journalNew by journalViewModelNew.entries.collectAsState()

    Spacer(modifier = Modifier.height(20.dp))
    HorizontalDivider(modifier = Modifier.fillMaxWidth())
    Spacer(modifier = Modifier.height(20.dp))

    val grouped = journalNew.groupBy { entries ->
        Instant.ofEpochMilli(entries.createdAt)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    }

    LazyColumn {
        grouped.forEach { (date, entriesOfDay) ->
            item {
                Text(date.toString())
                Spacer(modifier = Modifier.height(12.dp))
            }
            items(entriesOfDay) { entries ->
                MessageCard(
                    message = entries.text,
                    onDelete = { onDelete() },
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
    onDelete: () -> Unit

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

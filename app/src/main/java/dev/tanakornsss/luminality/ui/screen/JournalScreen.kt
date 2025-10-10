package dev.tanakornsss.luminality.ui.screen

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tanakornsss.luminality.data.JournalViewModel
import dev.tanakornsss.luminality.ui.component.CustomAlertDialog
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun JournalScreen(viewModel: JournalViewModel) {
    var textFieldValue by remember { mutableStateOf("") }

    val localDateTime = LocalDateTime.now()
    val formattedDate = localDateTime.format(
        DateTimeFormatter.ofPattern("dd-MM-yyyy"))

    var showDeleteDialog by remember { mutableStateOf(false) }
    var pendingDeleteMessage by remember { mutableStateOf<String?>(null) }
    var pendingDeleteDate by remember { mutableStateOf<String?>(null) }

    if (showDeleteDialog) {
        DeleteAlertDialog(
            onDismiss = {
                showDeleteDialog = false
            },
            onConfirm = {
                viewModel.deleteMessage(
                    pendingDeleteDate ?: "",
                    pendingDeleteMessage ?: ""
                )
                pendingDeleteDate = null
                pendingDeleteMessage = null
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
                            viewModel.addMessage(textFieldValue)
                            textFieldValue = ""
                        },
                        enabled = textFieldValue.isNotBlank(),
                        modifier = Modifier.height(56.dp)
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = null)
                    }
                }
                Spacer(modifier = Modifier.padding(vertical = 16.dp))
                Text("Current date time $formattedDate")
            }
            MessageSlider(viewModel) { date, message ->
                showDeleteDialog = true
                pendingDeleteDate = date
                pendingDeleteMessage = message
            }
        }
    }
}

@Composable
private fun MessageSlider(viewModel: JournalViewModel, onDelete: (String, String) -> Unit) {
    val journal by viewModel.journal.collectAsState()

    Spacer(modifier = Modifier.height(20.dp))
    HorizontalDivider(modifier = Modifier.fillMaxWidth())
    Spacer(modifier = Modifier.height(20.dp))
    LazyColumn {
        journal.entries.forEach { (date, messages) ->
            item {
                Text(date, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(12.dp))
            }
            items(messages) { msg ->
                MessageCard(msg) {
                    onDelete(date, msg)
                }
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

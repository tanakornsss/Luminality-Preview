package dev.tanakornsss.luminality.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun CustomAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    showDismiss: Boolean = true,
    dismissText: String = "Dismiss",
    confirmText: String = "Confirm"
) {
    AlertDialog(
        title = { Text(text = dialogTitle) },
        text = { Text(text = dialogText) },
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            TextButton(
                onClick = { onConfirmation() }
            ) { Text(confirmText) }
        },
        dismissButton = {
            if (showDismiss) {
                TextButton(
                    onClick = { onDismissRequest() }
                ) { Text(dismissText) }
            }
        }
    )
}
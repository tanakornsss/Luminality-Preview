package dev.tanakornsss.luminality.ui.screen.journal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tanakornsss.luminality.data.streak.Streak

@Composable
fun AddJournalScreen(
    innerPadding: PaddingValues,
    streak: Streak,
    textFieldValue: String,
    textFieldUpdate: (String) -> Unit,
    addEntry: () -> Unit
) {
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
                textFieldUpdate(it)
            },
            label = {
                Text("What's good today?")
            },
            trailingIcon = {
                IconButton(onClick = {
                    addEntry()
                }
                ) {
                    Icon(Icons.Outlined.Add, null)
                }
            }
        )
    }
}
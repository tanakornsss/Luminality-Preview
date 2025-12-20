package dev.tanakornsss.luminality.ui.screen.journal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.tanakornsss.luminality.R
import dev.tanakornsss.luminality.data.journal.JournalViewModel
import dev.tanakornsss.luminality.data.model.Journal
import java.time.Instant
import java.time.ZoneId

@Composable
fun JournalListScreen(
    innerPadding: PaddingValues,
    journalViewModel: JournalViewModel,
    pendingDeleteJournal: (Journal) -> Unit,
    deleteDialogTrigger: (Boolean) -> Unit,
) {
    Column(modifier = Modifier
        .padding(innerPadding)
        .fillMaxSize()
    ) {
        ListContent(journalViewModel) {
            deleteDialogTrigger(true)
            pendingDeleteJournal(it)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListContent(
    journalViewModel: JournalViewModel,
    onDelete: (Journal) -> Unit
) {
    val journalNew by journalViewModel.entries.collectAsState()

    Spacer(Modifier.height(16.dp))
    Text(
        text = stringResource(R.string.your_journeys),
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(horizontal = 16.dp)
    )

    Spacer(modifier = Modifier.height(20.dp))

    val grouped = journalNew.groupBy { entries ->
        Instant.ofEpochMilli(entries.createdAt)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    }

    LazyColumn {
        grouped.forEach { (date, entriesOfDay) ->
            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text(date.toString())
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
            items(entriesOfDay) { entries ->
                MessageCard(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    message = entries.text
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
    modifier: Modifier = Modifier,
    message: String,
) {
    var expanded by remember { mutableStateOf(false ) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Text(
            modifier = modifier,
            text = message,
            style = MaterialTheme.typography.bodyLarge,
        )
        Box {
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Filled.MoreVert, null)
            }
            Column {
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(stringResource(R.string.edit))
                                Icon(Icons.Filled.Edit, null)
                            }
                        },
                        onClick = { expanded = false }
                    )
                    DropdownMenuItem(
                        text = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(stringResource(R.string.delete))
                                Icon(Icons.Filled.Close, null)
                            }
                        },
                        onClick = { expanded = false }
                    )
                }
            }
        }
    }
}

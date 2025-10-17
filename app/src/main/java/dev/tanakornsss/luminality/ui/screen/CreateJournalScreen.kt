package dev.tanakornsss.luminality.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Devices.PIXEL_9
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.tanakornsss.luminality.ui.theme.LuminalityTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateJournalScreen(onNavigateBack: () -> Unit) {
    val localDate = LocalDate.now()
    val formattedDate = localDate
        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))

    var pendingSaveJournal by remember { mutableStateOf<List<String>>(emptyList()) }

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Create Journal")
                    Button(onClick = { }) {
                        Text("Save")
                    }
                }
            },
            navigationIcon = {
                IconButton(onClick = { onNavigateBack() }) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(32.dp))
        ) {
            AddJournalCard(
                formattedDate = formattedDate,
                pendingListSize = pendingSaveJournal.size
            )
        }
    }
}

@Composable
private fun AddJournalCard(
    formattedDate: String,
    pendingListSize: Int,
) {
    Card(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(20.dp)
        ) {
            Text("Date: $formattedDate")
            Spacer(modifier = Modifier.height(12.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(5) {
                    BulletedText("Input here")
                }
            }
        }
    }
    Spacer(modifier = Modifier.padding(16.dp))
}

@Composable
private fun BulletedText(content: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("•")
        Spacer(modifier = Modifier.width(8.dp))
        Text(content)
    }
}

@Composable
@Preview(device = PIXEL_9, showSystemUi = true)
private fun CreateJournalScreenPreview() {
    LuminalityTheme {
        CreateJournalScreen { }
    }
}

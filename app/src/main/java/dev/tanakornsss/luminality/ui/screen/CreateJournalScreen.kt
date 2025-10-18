package dev.tanakornsss.luminality.ui.screen

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Devices.PIXEL_9
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
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

    val isDarkMode = isSystemInDarkTheme()

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
                pendingList = pendingSaveJournal,
                isDarkMode = isDarkMode
            )
        }
    }
}

@Composable
private fun AddJournalCard(
    formattedDate: String,
    pendingList: List<String>,
    isDarkMode: Boolean
) {
    val color = if (isDarkMode) Color.White else Color.Black
    // Make lineHeight unspecified to avoid baseline mismatch with cursor
    val textStyle = MaterialTheme.typography.bodyLarge.copy(lineHeight = TextUnit.Unspecified)

    var textFieldState by remember { mutableStateOf("") }

    Card(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(20.dp)
        ) {
            Text("Date: $formattedDate")
            Spacer(modifier = Modifier.height(12.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(pendingList) { msg ->
                    BulletedText(msg)
                }
                item {
                    BasicTextField(
                        value = textFieldState,
                        onValueChange = { textFieldState = it },
                        textStyle = textStyle,
                        cursorBrush = SolidColor(color),
                        singleLine = true,
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                if (textFieldState.isEmpty()) {
                                    Text(
                                        text = "Enter text",
                                        style = textStyle,
                                        color = Color.Gray
                                    )
                                }
                                innerTextField()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    )
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

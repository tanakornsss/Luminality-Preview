package dev.tanakornsss.luminality.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices.PIXEL_9
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun LandingScreen() {
    var textFieldValue by remember { mutableStateOf("") }

    val localDateTime = LocalDateTime.now()
    val formattedDate = localDateTime.format(
        DateTimeFormatter.ofPattern("dd-MM-yyyy"))


    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .padding(horizontal = 16.dp)
            .fillMaxSize()
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
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
                        onClick = { },
                        modifier = Modifier.height(56.dp)
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = null)
                    }
                }
                Spacer(modifier = Modifier.padding(vertical = 16.dp))
                Text("Current date time $formattedDate")
            }
        }
    }
}

@Composable
@Preview(device = PIXEL_9, showSystemUi = true)
fun LandingScreenPreview() {
    LandingScreen()
}


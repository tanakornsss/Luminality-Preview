package dev.tanakornsss.luminality.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices.PIXEL_9
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.tanakornsss.luminality.ui.theme.LuminalityTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewJournalScreen() {
    Scaffold(
        topBar = { TopAppBar(title = { Text("App logo placeholder") }) },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
        ) {
            LazyRow(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()) {
                // Current color is for placeholder only
                items(7) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFEFB8C8))
                            .padding(vertical = 10.dp, horizontal = 14.dp)
                    ) {
                        Text("1")
                        Text("Day")
                    }
                }
            }
            Column(
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.End,
                modifier = Modifier.fillMaxSize()
                    .padding(horizontal = 12.dp)
            ) {
                FloatingActionButton(onClick = { }) {
                    Icon(Icons.Filled.Add, contentDescription = null)
                }
            }
        }
    }
}

@Composable
@Preview(device = PIXEL_9, showSystemUi = true)
fun NewJournalScreenPreview() {
    LuminalityTheme {
        NewJournalScreen()
    }
}
package dev.tanakornsss.luminality.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices.PIXEL_9
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.tanakornsss.luminality.ui.theme.LuminalityTheme
import java.time.DayOfWeek
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewJournalScreen() {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("App logo placeholder") }) },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
        ) {
           CalendarRow(selectedDate) {
                selectedDate = it
           }
            Column(
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.End,
                modifier = Modifier
                    .fillMaxSize()
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
private fun CalendarRow(
    selectedDate: LocalDate,
    onDateSelect: (LocalDate) -> Unit
) {
    val startOfWeek = selectedDate.with(DayOfWeek.MONDAY)
    val daysOfWeek = remember {
        (0..6).map { startOfWeek.plusDays(it.toLong()) }
    }

    LazyRow(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Current color is for placeholder only
        items(daysOfWeek) { day ->
            val isSelected = (day == selectedDate)
            val bgColor = if (isSelected) {
                Color(0xFFEFB8C8)
            }
            else {
                Color(0xFFF1D2D9)
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(bgColor)
                    .height(96.dp)
                    .width(56.dp)
                    .clickable {
                        onDateSelect(day)
                    }
            ) {
                Text(day.dayOfMonth.toString())
                Text(day.dayOfWeek.name.take(3)
                    .lowercase()
                    .replaceFirstChar { it.uppercase() }
                )
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
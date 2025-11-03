package dev.tanakornsss.luminality.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Devices.PIXEL_9
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.tanakornsss.luminality.ui.theme.LuminalityTheme
import java.time.DayOfWeek
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewJournalScreen(onNavigateCreateJournal: () -> Unit) {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    val navController = rememberNavController()
    val startDestination = BottomBarNavRoute.Home
    var selectedDestination by rememberSaveable {
        mutableIntStateOf(startDestination.ordinal)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                when (selectedDestination) {
                    BottomBarNavRoute.Home.ordinal -> Text("App logo here")
                    BottomBarNavRoute.Settings.ordinal -> Text("Settings")
                }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                BottomBarNavRoute.entries.forEachIndexed { idx, dest ->
                    NavigationBarItem(
                        selected = selectedDestination == idx,
                        onClick = {
                            navController.navigate(dest.name)
                            selectedDestination = idx
                        },
                        icon = {
                            Icon(
                                dest.icon,
                                contentDescription = dest.description
                            )
                        },
                        label = { Text(dest.label) }
                    )
                }
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomBarNavRoute.Home.name
        ) {
            composable(BottomBarNavRoute.Home.name) {
                JournalHome(
                    innerPadding = innerPadding,
                    selectedDate = selectedDate,
                    onUpdateSelectedDate = { selectedDate = it },
                    onNavigateCreateJournal = { onNavigateCreateJournal() }
                )
            }
            composable(BottomBarNavRoute.Settings.name) {
                SettingsScreen()
            }
        }
    }
}

private enum class BottomBarNavRoute(
    val label: String,
    val icon: ImageVector,
    val description: String
) {
    Home(
        "Home",
        Icons.Filled.Home,
        ""
    ),
    Settings(
        "Settings",
        Icons.Filled.Settings,
        ""
    ),
}

@Composable
private fun JournalHome(
    innerPadding: PaddingValues,
    selectedDate: LocalDate,
    onUpdateSelectedDate: (LocalDate) -> Unit,
    onNavigateCreateJournal: () -> Unit
) {
    Box(modifier = Modifier
        .padding(innerPadding)
        .fillMaxSize()
    ) {
        CalendarRow(selectedDate) {
            onUpdateSelectedDate(it)
        }
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 20.dp,
                    vertical = 16.dp
                )
        ) {
            FloatingActionButton(onClick = {
                onNavigateCreateJournal()
            }
            ) {
                Icon(Icons.Filled.Add, contentDescription = null)
            }
        }
    }
}

@Composable
private fun SettingsScreen() {
    Scaffold { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {

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
        NewJournalScreen { }
    }
}
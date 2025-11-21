package dev.tanakornsss.luminality.ui.screen.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Devices.PIXEL_9
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.tanakornsss.luminality.ui.screen.home.screens.JournalHome
import dev.tanakornsss.luminality.ui.screen.home.screens.SettingsScreen
import dev.tanakornsss.luminality.ui.theme.LuminalityTheme
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewJournalScreen(onNavigateCreateJournal: () -> Unit) {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    val navController = rememberNavController()
    val startDestination = BottomBarNavRoute.HOME
    var selectedDestination by rememberSaveable {
        mutableIntStateOf(startDestination.ordinal)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    when (selectedDestination) {
                        BottomBarNavRoute.HOME.ordinal -> Text("App logo here")
                        BottomBarNavRoute.SETTINGS.ordinal -> Text("Settings")
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
            startDestination = BottomBarNavRoute.HOME.name
        ) {
            composable(BottomBarNavRoute.HOME.name) {
                JournalHome(
                    innerPadding = innerPadding,
                    selectedDate = selectedDate,
                    onUpdateSelectedDate = { selectedDate = it },
                    onNavigateCreateJournal = { onNavigateCreateJournal() }
                )
            }
            composable(BottomBarNavRoute.SETTINGS.name) {
                SettingsScreen(innerPadding)
            }
        }
    }
}

private enum class BottomBarNavRoute(
    val label: String,
    val icon: ImageVector,
    val description: String
) {
    HOME(
        "Home",
        Icons.Filled.Home,
        ""
    ),
    SETTINGS(
        "Settings",
        Icons.Filled.Settings,
        ""
    ),
}

@Composable
@Preview(device = PIXEL_9, showSystemUi = true)
fun NewJournalScreenPreview() {
    LuminalityTheme {
        NewJournalScreen { }
    }
}

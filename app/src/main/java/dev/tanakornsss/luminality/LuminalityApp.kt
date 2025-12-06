package dev.tanakornsss.luminality

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.tanakornsss.luminality.data.journal.JournalViewModel
import dev.tanakornsss.luminality.data.journal.JournalViewModelFactory
import dev.tanakornsss.luminality.setting.SettingViewModel
import dev.tanakornsss.luminality.setting.SettingViewModelFactory
import dev.tanakornsss.luminality.ui.LuminalityScreen
import dev.tanakornsss.luminality.ui.screen.CreateJournalScreen
import dev.tanakornsss.luminality.ui.screen.JournalScreen
import dev.tanakornsss.luminality.ui.screen.home.NewJournalScreen
import dev.tanakornsss.luminality.ui.theme.LuminalityTheme

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun LuminalityApp(context: Context) {
    val journalViewModel: JournalViewModel =
        viewModel(factory = JournalViewModelFactory(context))
    val settingViewModel: SettingViewModel =
        viewModel(factory = SettingViewModelFactory(context))

    val navController = rememberNavController()

    LuminalityTheme {
        NavHost(
            navController = navController,
            startDestination = LuminalityScreen.Journal.name
        ) {
            composable(LuminalityScreen.Journal.name) {
                JournalScreen(
                    journalViewModel = journalViewModel,
                    context = context
                )
            }
            composable(LuminalityScreen.NewJournal.name) {
                NewJournalScreen(
                    context = context,
                    onNavigateCreateJournal = {
                        navController.navigate(LuminalityScreen.CreateJournal.name)
                    }
                )
            }
            composable(LuminalityScreen.CreateJournal.name) {
                CreateJournalScreen {
                    navController.popBackStack()
                }
            }
        }
    }
}

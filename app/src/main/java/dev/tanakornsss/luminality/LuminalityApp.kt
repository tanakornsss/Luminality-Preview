package dev.tanakornsss.luminality

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.tanakornsss.luminality.data.JournalViewModel
import dev.tanakornsss.luminality.data.JournalViewModelFactory
import dev.tanakornsss.luminality.ui.LuminalityScreen
import dev.tanakornsss.luminality.ui.screen.JournalScreen
import dev.tanakornsss.luminality.ui.theme.LuminalityTheme

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun LuminalityApp(context: Context) {
    val journalViewModel: JournalViewModel =
        viewModel(factory = JournalViewModelFactory(context))

    val navController = rememberNavController()

    LuminalityTheme {
        NavHost(
            navController = navController,
            startDestination = LuminalityScreen.TestLanding.name
        ) {
            composable(LuminalityScreen.TestLanding.name) {
                JournalScreen(journalViewModel, context)
            }
        }
    }
}

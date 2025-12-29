package dev.tanakornsss.luminality

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dev.tanakornsss.luminality.data.backup.BackupViewModel
import dev.tanakornsss.luminality.data.backup.BackupViewModelFactory
import dev.tanakornsss.luminality.data.journal.JournalViewModel
import dev.tanakornsss.luminality.data.journal.JournalViewModelFactory
import dev.tanakornsss.luminality.launch.LaunchViewModel
import dev.tanakornsss.luminality.launch.LaunchViewModelFactory
import dev.tanakornsss.luminality.setting.SettingViewModel
import dev.tanakornsss.luminality.setting.SettingViewModelFactory
import dev.tanakornsss.luminality.ui.LuminalityScreen
import dev.tanakornsss.luminality.ui.screen.JournalScreen
import dev.tanakornsss.luminality.ui.screen.SettingScreen
import dev.tanakornsss.luminality.ui.theme.LuminalityTheme

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun LuminalityApp(context: Context) {
    val journalViewModel: JournalViewModel =
        viewModel(factory = JournalViewModelFactory(context))
    val backupViewModel: BackupViewModel =
        viewModel(factory = BackupViewModelFactory(context))
    val settingViewModel: SettingViewModel =
        viewModel(factory = SettingViewModelFactory(context))
    val launchViewModel: LaunchViewModel =
        viewModel(factory = LaunchViewModelFactory(context))

    val settingState by settingViewModel.setting.collectAsState()
    val telemetryState = settingState.telemetryState

    LaunchedEffect(telemetryState) {
        FirebaseAnalytics.getInstance(context)
            .setAnalyticsCollectionEnabled(telemetryState)
        FirebaseCrashlytics.getInstance()
            .isCrashlyticsCollectionEnabled = telemetryState
    }

    val navController = rememberNavController()

    LuminalityTheme {
        NavHost(
            navController = navController,
            startDestination = LuminalityScreen.NewJournal.name
        ) {
            composable(LuminalityScreen.NewJournal.name) {
                JournalScreen(
                    journalViewModel = journalViewModel,
                    backupViewModel = backupViewModel,
                    onNavigateSetting = {
                        navController.navigate(LuminalityScreen.Setting.name)
                    }
                )
            }
            composable(LuminalityScreen.Setting.name) {
                SettingScreen(
                    onNavigateBack = { navController.popBackStack() },
                    settingViewModel = settingViewModel
                )
            }
        }
    }
}

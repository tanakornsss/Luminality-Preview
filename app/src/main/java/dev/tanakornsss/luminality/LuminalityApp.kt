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
import dev.tanakornsss.luminality.launch.LaunchEvent
import dev.tanakornsss.luminality.launch.LaunchViewModel
import dev.tanakornsss.luminality.launch.LaunchViewModelFactory
import dev.tanakornsss.luminality.setting.SettingViewModel
import dev.tanakornsss.luminality.setting.SettingViewModelFactory
import dev.tanakornsss.luminality.ui.LuminalityScreen
import dev.tanakornsss.luminality.ui.screen.JournalScreen
import dev.tanakornsss.luminality.ui.screen.OnboardScreen
import dev.tanakornsss.luminality.ui.screen.SettingScreen
import dev.tanakornsss.luminality.ui.screen.SplashScreen
import dev.tanakornsss.luminality.ui.screen.WhatsNewScreen
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

    LaunchedEffect(Unit) {
        launchViewModel.event.collect { event ->
            when (event) {
                LaunchEvent.ShowOnboarding ->
                    navController.navigate(LuminalityScreen.ONBOARD.name) {
                        popUpTo(0)
                    }
                LaunchEvent.ShowWhatsNew ->
                    navController.navigate(LuminalityScreen.WHATS_NEW.name) {
                        popUpTo(0)
                    }
                LaunchEvent.Normal ->
                    navController.navigate(LuminalityScreen.JOURNAL.name) {
                        popUpTo(0)
                    }
            }
        }
    }

    LuminalityTheme {
        NavHost(
            navController = navController,
            startDestination = LuminalityScreen.SPLASH.name
        ) {
            composable(LuminalityScreen.JOURNAL.name) {
                JournalScreen(
                    journalViewModel = journalViewModel,
                    backupViewModel = backupViewModel,
                    onNavigateSetting = {
                        navController.navigate(LuminalityScreen.SETTING.name)
                    }
                )
            }
            composable(LuminalityScreen.SETTING.name) {
                SettingScreen(
                    onNavigateBack = { navController.popBackStack() },
                    settingViewModel = settingViewModel
                )
            }
            composable(LuminalityScreen.ONBOARD.name) {
                OnboardScreen {
                    navController.navigate(LuminalityScreen.JOURNAL.name) {
                        popUpTo(0)
                    }
                }
            }
            composable(LuminalityScreen.WHATS_NEW.name) {
                WhatsNewScreen {
                    navController.navigate(LuminalityScreen.JOURNAL.name) {
                        popUpTo(0)
                    }
                }
            }
            composable(LuminalityScreen.SPLASH.name) {
                SplashScreen()
            }
        }
    }
}

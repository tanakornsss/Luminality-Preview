package dev.tanakornsss.luminality

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.tanakornsss.luminality.data.backup.BackupViewModel
import dev.tanakornsss.luminality.data.backup.BackupViewModelFactory
import dev.tanakornsss.luminality.data.journal.JournalViewModel
import dev.tanakornsss.luminality.data.journal.JournalViewModelFactory
import dev.tanakornsss.luminality.launch.LaunchEvent
import dev.tanakornsss.luminality.launch.LaunchViewModel
import dev.tanakornsss.luminality.ui.LuminalityScreen
import dev.tanakornsss.luminality.ui.screen.SettingScreen
import dev.tanakornsss.luminality.ui.screen.SplashScreen
import dev.tanakornsss.luminality.ui.screen.WhatsNewScreen
import dev.tanakornsss.luminality.ui.screen.intoduction.JournalScreen
import dev.tanakornsss.luminality.ui.screen.intoduction.OnboardScreen
import dev.tanakornsss.luminality.ui.theme.LuminalityTheme
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun LuminalityApp(context: Context) {
    val journalViewModel: JournalViewModel =
        viewModel(factory = JournalViewModelFactory(context))
    val backupViewModel: BackupViewModel =
        viewModel(factory = BackupViewModelFactory(context))
    val launchViewModel: LaunchViewModel = koinViewModel()

    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    var navDirection by remember { mutableStateOf(NavDirection.Push) }

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

    BackHandler(navController.previousBackStackEntry != null) {
        navDirection = NavDirection.Pop
        navController.popBackStack()
    }

    LuminalityTheme {
        AnimatedContent(
            targetState = currentRoute,
            transitionSpec = {
                if (navDirection == NavDirection.Push) {
                    slideInHorizontally { it } togetherWith
                            slideOutHorizontally { -it }
                }
                else {
                    slideInHorizontally { -it } togetherWith
                            slideOutHorizontally { it }
                }
            },
            contentKey = { it },
            label = "nav-animation"
        ) {
            NavHost(
                navController = navController,
                startDestination = LuminalityScreen.SPLASH.name
            ) {
                composable(LuminalityScreen.JOURNAL.name) {
                    JournalScreen(
                        journalViewModel = journalViewModel,
                        onNavigateSetting = {
                            navDirection = NavDirection.Push
                            navController.navigate(LuminalityScreen.SETTING.name)
                        }
                    )
                }
                composable(LuminalityScreen.SETTING.name) {
                    SettingScreen(
                        backupViewModel = backupViewModel,
                        onNavigateBack = {
                            navDirection = NavDirection.Pop
                            navController.popBackStack()
                        }
                    )
                }
                composable(LuminalityScreen.ONBOARD.name) {
                    OnboardScreen(
                        onReturn = {
                            navDirection = NavDirection.Push
                            navController.navigate(LuminalityScreen.JOURNAL.name) {
                                popUpTo(0)
                            }
                        }
                    )
                }
                composable(LuminalityScreen.WHATS_NEW.name) {
                    WhatsNewScreen {
                        navDirection = NavDirection.Push
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
}

enum class NavDirection {
    Push, Pop
}

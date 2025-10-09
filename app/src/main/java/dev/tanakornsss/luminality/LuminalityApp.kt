package dev.tanakornsss.luminality

import android.content.Context
import androidx.compose.runtime.Composable
import dev.tanakornsss.luminality.data.JournalViewModel
import dev.tanakornsss.luminality.ui.screen.JournalScreen
import dev.tanakornsss.luminality.ui.theme.LuminalityTheme

@Composable
fun LuminalityApp(context: Context) {
    val journalViewModel = JournalViewModel(context)

    LuminalityTheme {
        JournalScreen(journalViewModel)
    }
}
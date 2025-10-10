package dev.tanakornsss.luminality

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import dev.tanakornsss.luminality.data.JournalViewModel
import dev.tanakornsss.luminality.ui.screen.JournalScreen
import dev.tanakornsss.luminality.ui.theme.LuminalityTheme

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun LuminalityApp(context: Context) {
    val journalViewModel = JournalViewModel(context)

    LuminalityTheme {
        JournalScreen(journalViewModel, context)
    }
}

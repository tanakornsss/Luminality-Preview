package dev.tanakornsss.luminality.data.journal

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.tanakornsss.luminality.data.AppDatabase
import dev.tanakornsss.luminality.data.streak.StreakRepository

class JournalViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JournalViewModel::class.java)) {
            val db = AppDatabase.getInstance(context)

            val journalDao = db.journalDao()
            val journalRepository = JournalRepository(journalDao)

            val streakDao = db.streakDao()
            val streakRepository = StreakRepository(streakDao)

            @Suppress("UNCHECKED_CAST")
            return JournalViewModel(
                journalRepository = journalRepository,
                streakRepository = streakRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
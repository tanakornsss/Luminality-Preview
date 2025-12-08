package dev.tanakornsss.luminality.data.journal

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.tanakornsss.luminality.data.AppDatabase

class JournalViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JournalViewModel::class.java)) {
            val db = AppDatabase.getInstance(context)
            val dao = db.journalDao()
            val repo = JournalRepository(dao)

            @Suppress("UNCHECKED_CAST")
            return JournalViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
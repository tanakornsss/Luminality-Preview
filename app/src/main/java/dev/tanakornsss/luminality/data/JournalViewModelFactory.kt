package dev.tanakornsss.luminality.data

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class JournalViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JournalViewModel::class.java)) {
            val db = JournalDatabase.getInstance(context)
            val dao = db.journalDao()
            val repo = JournalRepository(dao)

            @Suppress("UNCHECKED_CAST")
            return JournalViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
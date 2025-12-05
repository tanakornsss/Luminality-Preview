package dev.tanakornsss.luminality.data

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class JournalViewModelFactoryNew(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JournalViewModelNew::class.java)) {
            val db = JournalDatabase.getInstance(context)
            val dao = db.journalDao()
            val repo = JournalRepositoryNew(dao)

            @Suppress("UNCHECKED_CAST")
            return JournalViewModelNew(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
package dev.tanakornsss.luminality.data.backup

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.tanakornsss.luminality.data.AppDatabase

class BackupViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BackupViewModel::class.java)) {
            val db = AppDatabase.getInstance(context)

            val journalDao = db.journalDao()
            val streakDao = db.streakDao()

            val backupRepository = BackupRepository(journalDao, streakDao)

            @Suppress("UNCHECKED_CAST")
            return BackupViewModel(
                context = context,
                backupRepository = backupRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
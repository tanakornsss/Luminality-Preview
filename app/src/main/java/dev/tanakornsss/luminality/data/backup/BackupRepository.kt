package dev.tanakornsss.luminality.data.backup

import android.content.Context
import android.net.Uri
import com.google.gson.GsonBuilder
import dev.tanakornsss.luminality.data.journal.JournalDao
import dev.tanakornsss.luminality.data.model.Backup
import dev.tanakornsss.luminality.data.streak.StreakDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BackupRepository(
    private val journalDao: JournalDao,
    private val streakDao: StreakDao,
    private val context: Context
) {
    private val gson = GsonBuilder().setPrettyPrinting().create()

    suspend fun exportJson(uri: Uri) = withContext(Dispatchers.IO) {
        val journal = journalDao.exportAllEntries()
        val streak = streakDao.exportAllStreak()

        val data = Backup(
            journal = journal,
            streak = streak
        )

        val res = gson.toJson(data)

        context.contentResolver.openOutputStream(uri)?.use {
            it.write(res.toByteArray())
        }
    }

    suspend fun importJson(uri: Uri) = withContext(Dispatchers.IO) {
        val json = context.contentResolver.openInputStream(uri)?.use {
            it.readBytes().decodeToString()
        }

        if (json == null) return@withContext

        val backup = gson.fromJson(json, Backup::class.java)

        journalDao.deleteAllEntries()
        streakDao.deleteAllStreak()

        journalDao.replaceAllEntries(backup.journal)
        streakDao.replaceAllStreak(backup.streak)
    }
}
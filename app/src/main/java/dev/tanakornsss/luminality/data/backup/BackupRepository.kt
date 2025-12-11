package dev.tanakornsss.luminality.data.backup

import com.google.gson.GsonBuilder
import dev.tanakornsss.luminality.data.journal.JournalDao
import dev.tanakornsss.luminality.data.streak.StreakDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BackupRepository(
    private val journalDao: JournalDao,
    private val streakDao: StreakDao
) {
    private val gson = GsonBuilder().setPrettyPrinting().create()

    suspend fun exportJson(): String = withContext(Dispatchers.IO) {
        val journal = journalDao.exportAllEntries()
        val streak = streakDao.exportAllStreak()

        val data = Backup(
            journal = journal,
            streak = streak
        )

        gson.toJson(data)
    }

    suspend fun importJson(json: String) = withContext(Dispatchers.IO) {
        val backup = gson.fromJson(json, Backup::class.java)

        journalDao.deleteAllEntries()
        streakDao.deleteAllStreak()

        journalDao.replaceAllEntries(backup.journal)
        streakDao.replaceAllStreak(backup.streak)
    }
}
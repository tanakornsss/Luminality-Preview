package dev.tanakornsss.luminality.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.tanakornsss.luminality.data.journal.Journal
import dev.tanakornsss.luminality.data.journal.JournalDao
import dev.tanakornsss.luminality.data.streak.Streak
import dev.tanakornsss.luminality.data.streak.StreakDao

@Database(
    entities = [
        Journal::class,
        Streak::class
    ],
    exportSchema = false,
    version = 4
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun journalDao(): JournalDao
    abstract fun streakDao(): StreakDao
}
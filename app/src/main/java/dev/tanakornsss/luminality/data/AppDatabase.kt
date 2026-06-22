package dev.tanakornsss.luminality.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_db"
                )
                .fallbackToDestructiveMigration(false)
                .build().also { INSTANCE = it }
            }
    }
}
package dev.tanakornsss.luminality.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [JournalEntries::class], version = 1)
abstract class JournalDatabase : RoomDatabase() {
    abstract fun journalDao(): JournalDao

    companion object {
        @Volatile private var INSTANCE: JournalDatabase? = null

        fun getInstance(context: Context): JournalDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    JournalDatabase::class.java,
                    "journal_db"
                ).build().also { INSTANCE = it }
            }
    }
}

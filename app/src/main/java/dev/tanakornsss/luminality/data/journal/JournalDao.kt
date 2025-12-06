package dev.tanakornsss.luminality.data.journal

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface JournalDao {
    @Query("SELECT * FROM journal_table ORDER BY createdAt DESC")
    fun getAllEntries(): Flow<List<Journal>>

    @Insert
    suspend fun insertEntry(entry: Journal)

    @Delete
    suspend fun deleteEntry(entry: Journal)

    @Update
    suspend fun updateEntry(entry: Journal)
}
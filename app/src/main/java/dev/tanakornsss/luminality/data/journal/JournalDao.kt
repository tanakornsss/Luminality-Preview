package dev.tanakornsss.luminality.data.journal

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import dev.tanakornsss.luminality.data.model.Journal
import kotlinx.coroutines.flow.Flow

@Dao
interface JournalDao {
    @Query("SELECT * FROM journal_table ORDER BY createdAt DESC")
    fun getAllEntries(): Flow<List<Journal>>

    @Insert
    suspend fun insertEntry(entry: Journal)

    @Update
    suspend fun updateEntry(entry: Journal)

    @Delete
    suspend fun deleteEntry(entry: Journal)

    @Query("SELECT * FROM journal_table")
    fun exportAllEntries(): List<Journal>

    @Insert(onConflict = REPLACE)
    fun replaceAllEntries(journal: List<Journal>)

    @Query("DELETE from journal_table")
    fun deleteAllEntries()
}
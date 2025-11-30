package dev.tanakornsss.luminality.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "journal_table")
data class JournalEntries(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val text: String,
    val createdAt: Long = System.currentTimeMillis()
)

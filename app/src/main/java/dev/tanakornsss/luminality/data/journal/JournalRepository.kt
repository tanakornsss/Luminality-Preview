package dev.tanakornsss.luminality.data.journal

interface JournalRepository {
    suspend fun addEntry(text: String)
    suspend fun deleteEntry(entry: Journal)
    suspend fun updateEntry(entry: Journal)
}
package dev.tanakornsss.luminality.data

import kotlinx.coroutines.flow.Flow

class JournalRepository(private val dao: JournalDao) {
    val entries: Flow<List<JournalEntries>> = dao.getAllEntries()

    suspend fun addEntry(text: String) {
        dao.insertEntry(JournalEntries(text = text))
    }

    suspend fun deleteEntry(entry: JournalEntries) {
        dao.deleteEntry(entry)
    }

    suspend fun updateEntry(entry: JournalEntries) {
        dao.updateEntry(entry)
    }
}
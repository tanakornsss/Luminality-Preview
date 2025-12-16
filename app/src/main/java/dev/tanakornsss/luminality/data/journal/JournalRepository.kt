package dev.tanakornsss.luminality.data.journal

import dev.tanakornsss.luminality.data.model.Journal
import kotlinx.coroutines.flow.Flow

class JournalRepository(private val dao: JournalDao) {
    val entries: Flow<List<Journal>> = dao.getAllEntries()

    suspend fun addEntry(text: String) {
        dao.insertEntry(Journal(text = text))
    }

    suspend fun deleteEntry(entry: Journal) {
        dao.deleteEntry(entry)
    }

    suspend fun updateEntry(entry: Journal) {
        dao.updateEntry(entry)
    }
}
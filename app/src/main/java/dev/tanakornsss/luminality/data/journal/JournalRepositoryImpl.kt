package dev.tanakornsss.luminality.data.journal

import kotlinx.coroutines.flow.Flow

class JournalRepositoryImpl(private val dao: JournalDao): JournalRepository {
    val entries: Flow<List<Journal>> = dao.getAllEntries()

    override suspend fun addEntry(text: String) {
        dao.insertEntry(Journal(text = text))
    }

    override suspend fun deleteEntry(entry: Journal) {
        dao.deleteEntry(entry)
    }

    override suspend fun updateEntry(entry: Journal) {
        dao.updateEntry(entry)
    }
}
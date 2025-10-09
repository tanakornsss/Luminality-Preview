package dev.tanakornsss.luminality.data

import android.content.Context
import java.io.File

const val FILE_NAME = "journal.json"

class JournalRepository(context: Context) {
    private val file = File(context.filesDir, FILE_NAME)

    fun saveJournal(journal: Journal) {
        val jsonString = JsonUtil.json
            .encodeToString(Journal.serializer(), journal)
        file.writeText(jsonString)
    }

    fun editJournal(date: String, message: String): Journal {
        val current = loadJournal()
        val updatedEntries = current.entries.toMutableMap()

        val messages = updatedEntries[date]?.toMutableList() ?: mutableListOf()
        messages.add(message)
        updatedEntries[date] = messages

        val updatedJournal = current.copy(entries = updatedEntries)
        saveJournal(updatedJournal)
        return updatedJournal
    }

    fun loadJournal(): Journal {
        if (file.exists()) {
            val jsonString = file.readText()
            val res = JsonUtil.json
                .decodeFromString(Journal.serializer(), jsonString)
            return res
        }
        else return Journal()
    }
}

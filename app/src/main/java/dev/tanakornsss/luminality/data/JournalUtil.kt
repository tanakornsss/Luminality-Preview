package dev.tanakornsss.luminality.data

import android.content.Context
import kotlinx.serialization.json.Json
import java.io.File

const val FILE_NAME = "journal.json"

object JsonUtil {
    val json = Json { prettyPrint = true }
}

fun saveJournal(context: Context, journal: Journal) {
    val jsonString = JsonUtil.json
        .encodeToString(journal)
    val file = File(context.filesDir, FILE_NAME)
    file.writeText(jsonString)
}

fun loadJournal(context: Context): Journal {
    val file = File(context.filesDir, FILE_NAME)

    if (file.exists()) {
        val jsonString = file.readText()
        val res = JsonUtil.json.decodeFromString<Journal>(jsonString)
        return res
    }
    else return Journal()
}

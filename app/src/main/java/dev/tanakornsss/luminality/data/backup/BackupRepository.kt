package dev.tanakornsss.luminality.data.backup

import android.net.Uri

interface BackupRepository {
    suspend fun exportJson(uri: Uri): Unit?
    suspend fun importJson(uri: Uri)
}
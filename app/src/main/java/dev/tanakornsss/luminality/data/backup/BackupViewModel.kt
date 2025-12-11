package dev.tanakornsss.luminality.data.backup

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BackupViewModel(
    private val context: Context,
    private val backupRepository: BackupRepository
) : ViewModel() {
    fun exportToUri(uri: Uri) {
        viewModelScope.launch {
            val content = backupRepository.exportJson()
            withContext(Dispatchers.IO) {
                context.contentResolver.openOutputStream(uri)?.use {
                    it.write(content.toByteArray())
                }
            }
        }
    }

    fun importFromUri(uri: Uri) {
        viewModelScope.launch {
            val json = withContext(Dispatchers.IO) {
                context.contentResolver.openInputStream(uri)?.use {
                    it.readBytes().decodeToString()
                }
            }
            if (json != null) {
                backupRepository.importJson(json)
            }
        }
    }
}
package dev.tanakornsss.luminality.data.backup

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BackupViewModel(private val backupRepository: BackupRepository) : ViewModel() {
    fun exportToUri(uri: Uri) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                backupRepository.exportJson(uri)
            }
        }
    }

    fun importFromUri(uri: Uri) {
        viewModelScope.launch {
            backupRepository.importJson(uri)
        }
    }
}

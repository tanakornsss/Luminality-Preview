package dev.tanakornsss.luminality.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class JournalViewModelNew(private val journalRepositoryNew: JournalRepositoryNew) : ViewModel() {
    val entries = journalRepositoryNew.entries.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        emptyList()
    )

    fun addEntry(text: String) {
        viewModelScope.launch {
            journalRepositoryNew.addEntry(text)
        }
    }

    fun deleteEntry(entries: JournalEntries) {
        viewModelScope.launch {
            journalRepositoryNew.deleteEntry(entries)
        }
    }

    fun updateEntry(entries: JournalEntries) {
        viewModelScope.launch {
            journalRepositoryNew.updateEntry(entries)
        }
    }
}
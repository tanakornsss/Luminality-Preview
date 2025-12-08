package dev.tanakornsss.luminality.data.journal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class JournalViewModel(private val journalRepository: JournalRepository) : ViewModel() {
    val entries = journalRepository.entries.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        emptyList()
    )

    fun addEntry(text: String) {
        viewModelScope.launch {
            journalRepository.addEntry(text)
        }
    }

    fun deleteEntry(entries: Journal) {
        viewModelScope.launch {
            journalRepository.deleteEntry(entries)
        }
    }

    fun updateEntry(entries: Journal) {
        viewModelScope.launch {
            journalRepository.updateEntry(entries)
        }
    }
}
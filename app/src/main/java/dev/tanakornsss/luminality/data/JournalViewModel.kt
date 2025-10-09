package dev.tanakornsss.luminality.data

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class JournalViewModel(context: Context): ViewModel() {
    private val repository = JournalRepository(context)

    private val _journal = MutableStateFlow(Journal())
    val journal = _journal.asStateFlow()

    init {
        viewModelScope.launch {
            _journal.value = repository.loadJournal()
        }
    }

    fun addMessage(message: String) {
        viewModelScope.launch {
            val localDateTime = LocalDateTime.now()
            val today = localDateTime.format(
                DateTimeFormatter.ofPattern("dd-MM-yyyy"))

            val updated = repository.addEntry(today, message)
            _journal.value = updated
        }
    }
}

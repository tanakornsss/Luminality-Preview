package dev.tanakornsss.luminality.data.journal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tanakornsss.luminality.data.model.Journal
import dev.tanakornsss.luminality.data.streak.Streak
import dev.tanakornsss.luminality.data.streak.StreakRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class JournalViewModel(
    private val journalRepository: JournalRepository,
    private val streakRepository: StreakRepository,
) : ViewModel() {
    val entries = journalRepository.entries.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        emptyList()
    )

    val streak = streakRepository.streakFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        Streak(
            currentStreak = 0,
            lastUsedDate = 0
        )
    )

    fun addEntry(text: String) {
        viewModelScope.launch {
            journalRepository.addEntry(text)
            streakRepository.updateStreak(System.currentTimeMillis())
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
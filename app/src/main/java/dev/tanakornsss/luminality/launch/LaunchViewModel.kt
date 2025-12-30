package dev.tanakornsss.luminality.launch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LaunchViewModel(private val launchRepository: LaunchRepository) : ViewModel() {

    val launchType = launchRepository.launchType
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            LaunchType.NORMAL
        )

    init {
        viewModelScope.launch {
            launchRepository.markLaunched()
        }
    }

}
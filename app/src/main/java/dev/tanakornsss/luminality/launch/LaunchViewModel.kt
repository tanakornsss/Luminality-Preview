package dev.tanakornsss.luminality.launch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

sealed class LaunchEvent {
    object ShowOnboarding : LaunchEvent()
    object ShowWhatsNew : LaunchEvent()
    object Normal : LaunchEvent()
}

class LaunchViewModel(private val launchRepository: LaunchRepository) : ViewModel() {
    private val _event = MutableSharedFlow<LaunchEvent>()
    val event = _event.asSharedFlow()

    init {
        viewModelScope.launch {
            val type = launchRepository.getLaunchTypeOnce()
            when (type) {
                LaunchType.FIRST_INSTALL ->
                    _event.emit(LaunchEvent.ShowOnboarding)
                LaunchType.FIRST_AFTER_UPDATE ->
                    _event.emit(LaunchEvent.ShowWhatsNew)
                LaunchType.NORMAL ->
                    _event.emit(LaunchEvent.Normal)
            }
            launchRepository.markLaunched()
        }
    }
}

package dev.tanakornsss.luminality.launch

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LaunchViewModel(private val launchRepository: LaunchRepository) : ViewModel() {
    private val _launch = MutableStateFlow(Launch())
    val launch = _launch.asStateFlow()


}
package com.sid.measure.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sid.data.data.usecase.CheckArCoreSupportUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeasureViewModel @Inject constructor(
    private val checkArCoreSupportUseCase: CheckArCoreSupportUseCase
) : ViewModel() {
    internal var uiState = MutableStateFlow<UiState>(UiState.EmptyState)
        private set

    init {
        start()
    }

    private fun start() {
        viewModelScope.launch {
            val isArCoreSupported = checkArCoreSupportUseCase()
            if (isArCoreSupported){
                uiState.emit(UiState.MeasureState)
            }else{
                uiState.emit(UiState.ARNotSupportedState)
            }
        }
    }
}
package com.sid.home.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sid.data.data.usecase.GetAllMeasurementsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllMeasurementsUseCase: GetAllMeasurementsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    internal val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        fetchMeasurements()
    }

    private fun fetchMeasurements() {
        viewModelScope.launch {
            getAllMeasurementsUseCase().collectLatest { measurements ->
                if (measurements.isEmpty()) {
                    _uiState.emit(UiState.EmptyState)
                } else {
                    _uiState.emit(UiState.HomeState(measurements))
                }
            }
        }
    }

}
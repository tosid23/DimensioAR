package com.sid.home.domain

import com.sid.data.data.entity.MeasurementEntity

internal sealed class UiState {
    data object Loading : UiState()
    data object EmptyState : UiState()
    data class HomeState(val measurements: List<MeasurementEntity>) : UiState()
}
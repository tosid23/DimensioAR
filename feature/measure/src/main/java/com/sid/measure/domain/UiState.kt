package com.sid.measure.domain

internal sealed class UiState {
    data object EmptyState : UiState()
    data object MeasureState : UiState()
}
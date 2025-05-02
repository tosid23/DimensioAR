package com.sid.measure.domain

internal sealed class UiState {
    data object EmptyState : UiState()
    data object CameraPermissionNotGrantedState: UiState()
    data object ARNotSupportedState : UiState()
    data object ArCoreNotInstalledState : UiState()
    data object MeasureState : UiState()
}
package com.sid.measure.domain

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sid.common.camera.usecase.CheckCameraPermissionUseCase
import com.sid.common.camera.usecase.RequestCameraPermissionUseCase
import com.sid.measure.data.usecase.CheckArCoreInstallationUseCase
import com.sid.measure.data.usecase.CheckArCoreSupportUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeasureViewModel @Inject constructor(
    private val checkArCoreSupportUseCase: CheckArCoreSupportUseCase,
    private val checkArCoreInstallationUseCase: CheckArCoreInstallationUseCase,
    private val checkCameraPermissionUseCase: CheckCameraPermissionUseCase,
    private val requestCameraPermissionUseCase: RequestCameraPermissionUseCase
) : ViewModel() {
    internal var uiState = MutableStateFlow<UiState>(UiState.EmptyState)
        private set

    internal fun start(activity: Activity) {
        viewModelScope.launch {
            val isCameraPermissionGranted = checkCameraPermissionUseCase(activity)
            if (isCameraPermissionGranted) {
                checkArCoreSupportAndContinue(activity)
            } else {
                requestCameraPermissionUseCase(activity)
                uiState.emit(UiState.CameraPermissionNotGrantedState)
            }
        }
    }

    private fun checkArCoreSupportAndContinue(activity: Activity) {
        viewModelScope.launch {
            val isArCoreSupported = checkArCoreSupportUseCase()
            if (isArCoreSupported) {
                val isArCoreInstalled = checkArCoreInstallationUseCase(activity)
                if (isArCoreInstalled) {
                    uiState.emit(UiState.MeasureState)
                } else {
                    uiState.emit(UiState.ArCoreNotInstalledState)
                }
            } else {
                uiState.emit(UiState.ARNotSupportedState)
            }
        }
    }

    internal fun onPermissionResult(activity: Activity, isGranted: Boolean) {
        viewModelScope.launch {
            if (isGranted) {
                checkArCoreSupportAndContinue(activity)
            } else {
                uiState.emit(UiState.CameraPermissionNotGrantedState)
            }
        }
    }
}
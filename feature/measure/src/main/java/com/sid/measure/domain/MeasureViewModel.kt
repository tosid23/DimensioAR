package com.sid.measure.domain

import android.app.Activity
import android.opengl.GLSurfaceView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ar.core.Config
import com.google.ar.core.Session
import com.sid.common.camera.usecase.CheckCameraPermissionUseCase
import com.sid.common.camera.usecase.RequestCameraPermissionUseCase
import com.sid.measure.data.usecase.CheckArCoreInstallationUseCase
import com.sid.measure.data.usecase.CheckArCoreSupportUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
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

    private var arSession: Session? = null
    private var arConfig: Config? = null
    private var surfaceView: GLSurfaceView? = null

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
                    //Create the AR configuration
                    arSession = Session(activity)
                    arConfig = Config(arSession)

                    //Set the config to the session
                    arSession?.configure(arConfig)

                    //create the surfaceView
                    surfaceView = GLSurfaceView(activity)

                    uiState.emit(UiState.MeasureState(arSession, arConfig, surfaceView))
                } else {
                    uiState.emit(UiState.ArCoreNotInstalledState)
                }
            } else {
                uiState.emit(UiState.ARNotSupportedState)
            }
        }
    }

    internal fun onPause() {
        Timber.e("onPause called")
        arSession?.pause()
    }

    internal fun onResume() {
        Timber.e("onResume called")
        arSession?.resume()
    }
}
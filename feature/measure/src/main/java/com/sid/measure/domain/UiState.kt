package com.sid.measure.domain


import android.opengl.GLSurfaceView
import com.google.ar.core.Config
import com.google.ar.core.Session

internal sealed class UiState {
    data object EmptyState : UiState()
    data object CameraPermissionNotGrantedState : UiState()
    data object ARNotSupportedState : UiState()
    data object ArCoreNotInstalledState : UiState()
    data class MeasureState(
        val session: Session?,
        val config: Config?,
        val surfaceView: GLSurfaceView?
    ) : UiState()
}
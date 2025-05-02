package com.sid.common.camera.usecase

import android.Manifest
import android.app.Activity
import androidx.core.app.ActivityCompat
import javax.inject.Inject

class RequestCameraPermissionUseCase @Inject constructor() {
    operator fun invoke(activity: Activity) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST_CODE
        )
    }

    companion object {
        const val CAMERA_PERMISSION_REQUEST_CODE = 100
    }
}
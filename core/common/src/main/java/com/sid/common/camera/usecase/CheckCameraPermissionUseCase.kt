package com.sid.common.camera.usecase

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import javax.inject.Inject

class CheckCameraPermissionUseCase @Inject constructor() {
    operator fun invoke(activity: Activity): Boolean {
        return ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }
}

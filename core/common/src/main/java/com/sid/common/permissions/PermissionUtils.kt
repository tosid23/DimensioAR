package com.sid.common.permissions

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

object PermissionUtils {
    fun checkCameraPermission(activity: Activity): Boolean {
        val permissionStatus = ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.CAMERA
        )
        return permissionStatus == PackageManager.PERMISSION_GRANTED
    }
}
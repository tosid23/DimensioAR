package com.sid.measure.ui

import android.Manifest
import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.sid.common.permissions.PermissionUtils.checkCameraPermission
import com.sid.measure.domain.MeasureViewModel
import com.sid.measure.domain.UiState
import com.sid.measure.ui.widgets.ARNotInstalledLayout
import com.sid.measure.ui.widgets.ARNotSupportedLayout
import com.sid.measure.ui.widgets.TopBar
import com.sid.widgets.EmptyLayout

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MeasureScreen(
    viewModel: MeasureViewModel = hiltViewModel(),
    onBackPressed: () -> Unit = {}
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    val context = LocalContext.current
    val activity = context as Activity

    var permissionGranted by remember { mutableStateOf(false) }

    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    if (cameraPermissionState.status.isGranted) {
        permissionGranted = true
    } else {
        permissionGranted = false
    }

    LaunchedEffect(key1 = permissionGranted) {
        if (permissionGranted) {
            viewModel.start(activity)
        } else {
            cameraPermissionState.launchPermissionRequest()
        }
    }

    LaunchedEffect(key1 = state) {
        if (state is UiState.CameraPermissionNotGrantedState && checkCameraPermission(activity)) {
            cameraPermissionState.launchPermissionRequest()
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { TopBar(onBackPressed) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            HandleUiStates(state)
        }
    }
}

@Composable
private fun HandleUiStates(state: UiState) {
    when (state) {
        UiState.EmptyState -> EmptyLayout()
        UiState.ARNotSupportedState -> ARNotSupportedLayout()
        UiState.ArCoreNotInstalledState -> ARNotInstalledLayout()
        UiState.MeasureState -> {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Yay! 🥳 AR is supported on this device")
            }
        }

        else -> EmptyLayout()
    }
}






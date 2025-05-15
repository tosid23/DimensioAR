package com.sid.measure.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.graphics.SurfaceTexture
import android.opengl.GLES11Ext
import android.opengl.GLES30
import android.opengl.GLSurfaceView
import android.os.Build
import android.view.Surface
import android.view.WindowManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.ar.core.Config
import com.google.ar.core.Session
import com.sid.common.permissions.PermissionUtils.checkCameraPermission
import com.sid.measure.domain.MeasureViewModel
import com.sid.measure.domain.UiState
import com.sid.measure.ui.widgets.ARNotInstalledLayout
import com.sid.measure.ui.widgets.ARNotSupportedLayout
import com.sid.measure.ui.widgets.TopBar
import com.sid.widgets.EmptyLayout
import timber.log.Timber
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

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

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    viewModel.onResume()
                    Timber.e("onResume")
                }

                Lifecycle.Event.ON_PAUSE -> {
                    viewModel.onPause()
                    Timber.e("onPause")
                }

                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
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
        is UiState.MeasureState -> {
            ArViewLayout(state.surfaceView, state.session, state.config)
        }

        else -> EmptyLayout()
    }
}

@Composable
private fun ArViewLayout(glSurfaceView: GLSurfaceView?, session: Session?, config: Config?) {
    var canDrawFrame by remember { mutableStateOf(false) }
    glSurfaceView?.let { surfaceView ->
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            AndroidView(
                factory = { context ->
                    glSurfaceView.apply {
                        preserveEGLContextOnPause = true
                        setEGLContextClientVersion(3)

                        setRenderer(object : GLSurfaceView.Renderer {

                            private var cameraTextureId: Int = 0
                            private var cameraSurface: Surface? = null
                            private var surfaceTexture: SurfaceTexture? = null
                            private var isSurfaceCreated = false

                            override fun onSurfaceCreated(
                                gl: GL10?,
                                config: EGLConfig?
                            ) {
                                Timber.e("onSurfaceCreated called")
                                isSurfaceCreated = true
                                canDrawFrame = true
                                val textures = IntArray(1)
                                GLES30.glGenTextures(1, textures, 0)
                                cameraTextureId = textures[0]
                                Timber.e("cameraTextureId: $cameraTextureId")

                                // Define texture parameters
                                GLES30.glBindTexture(
                                    GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                                    cameraTextureId
                                )
                                GLES30.glTexParameteri(
                                    GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                                    GL10.GL_TEXTURE_MIN_FILTER,
                                    GL10.GL_NEAREST
                                )
                                GLES30.glTexParameteri(
                                    GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                                    GL10.GL_TEXTURE_MAG_FILTER,
                                    GL10.GL_NEAREST
                                )

                                // Tell ARCore about the texture
                                session?.setCameraTextureNames(intArrayOf(cameraTextureId))
                                Timber.e("setCameraTextureNames called")

                                // Create a SurfaceTexture and set its texture ID
                                surfaceTexture = SurfaceTexture(cameraTextureId)
                                Timber.e("surfaceTexture created")
                                // Create a Surface using the SurfaceTexture
                                cameraSurface = Surface(surfaceTexture)
                                Timber.e("cameraSurface created")

                                // Resume the session
                                session?.resume()
                                Timber.e("session resumed")
                            }

                            override fun onSurfaceChanged(
                                gl: GL10?,
                                width: Int,
                                height: Int
                            ) {
                                Timber.e("onSurfaceChanged called, width: $width, height: $height")
                                // Start rendering to the camera
                                val windowManager = context.getSystemService(Context.WINDOW_SERVICE)
                                        as WindowManager
                                val rotation = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                    context.display.rotation
                                } else {
                                    windowManager.defaultDisplay.rotation
                                }
                                Timber.e("rotation: $rotation")

                                session?.setDisplayGeometry(
                                    rotation,
                                    width,
                                    height
                                )
                                Timber.e("setDisplayGeometry called")
                            }

                            override fun onDrawFrame(gl: GL10?) {
                                if (canDrawFrame) {
                                    // Get the ARCore frame
                                    val frame = session?.update()
                                    Timber.e("frame updated: $frame")
                                    try {
                                        frame?.let {
                                            if (it.timestamp != 0L) {
                                                Timber.e("frame.timestamp: ${it.timestamp}")
                                                // Set the camera surface to the frame
                                            } else {
                                                Timber.e("frame.timestamp is 0, frame not valid")
                                            }
                                        }

                                    } catch (exception: Exception) {
                                        Timber.e("error ${exception.message}")
                                    }
                                }
                            }
                        })
                        renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY
                    }
                },
                modifier = Modifier.fillMaxSize()
            )

            DisposableEffect(Unit) {
                onDispose {
                    canDrawFrame = false
                }
            }
        }
    }
}






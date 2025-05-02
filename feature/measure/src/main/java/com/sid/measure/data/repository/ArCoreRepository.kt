package com.sid.measure.data.repository

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.google.ar.core.ArCoreApk
import com.google.ar.core.Session
import com.google.ar.core.exceptions.UnavailableUserDeclinedInstallationException
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface ArCoreRepository {
    fun isArCoreSupported(): Boolean
    fun requestInstallArCore(activity: Activity): Boolean
}

class ArCoreRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ArCoreRepository {

    private var mSession: Session? = null
    private var mUserRequestedInstall = true

    override fun isArCoreSupported(): Boolean {
        val availability = ArCoreApk.getInstance().checkAvailability(context)
        return availability.isSupported
    }

    override fun requestInstallArCore(activity: Activity): Boolean {
        try {
            if (mSession == null) {
                when (ArCoreApk.getInstance().requestInstall(activity, mUserRequestedInstall)) {
                    ArCoreApk.InstallStatus.INSTALLED -> {
                        // Success: Safe to create the AR session.
                        mSession = Session(context)
                        return true
                    }

                    ArCoreApk.InstallStatus.INSTALL_REQUESTED -> {
                        // When this method returns `INSTALL_REQUESTED`:
                        // 1. ARCore pauses this activity.
                        // 2. ARCore prompts the user to install or update Google Play
                        //    Services for AR (market://details?id=com.google.ar.core).
                        // 3. ARCore downloads the latest device profile data.
                        // 4. ARCore resumes this activity. The next invocation of
                        //    requestInstall() will either return `INSTALLED` or throw an
                        //    exception if the installation or update did not succeed.
                        mUserRequestedInstall = false
                        return false
                    }
                }
            }
        } catch (e: UnavailableUserDeclinedInstallationException) {
            // Display an appropriate message to the user and return gracefully.
            Toast.makeText(context, "Installation of ARCore is needed $e", Toast.LENGTH_LONG)
                .show()
            return false
        } catch (e: Exception) {
            Toast.makeText(context, "Error in ARCore installation $e", Toast.LENGTH_LONG)
                .show()
            return false
        }
        return true
    }
}
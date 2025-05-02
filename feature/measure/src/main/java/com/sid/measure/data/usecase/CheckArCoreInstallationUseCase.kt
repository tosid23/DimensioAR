package com.sid.measure.data.usecase

import android.app.Activity
import com.sid.measure.data.repository.ArCoreRepository
import javax.inject.Inject

class CheckArCoreInstallationUseCase @Inject constructor(
    private val arCoreRepository: ArCoreRepository
) {
    operator fun invoke(activity: Activity): Boolean {
        return arCoreRepository.requestInstallArCore(activity)
    }
}
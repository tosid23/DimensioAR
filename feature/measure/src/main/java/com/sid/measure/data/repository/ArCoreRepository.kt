package com.sid.measure.data.repository

import android.content.Context
import com.google.ar.core.ArCoreApk
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface ArCoreRepository {
    fun isArCoreSupported(): Boolean
}

class ArCoreRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ArCoreRepository {
    override fun isArCoreSupported(): Boolean {
        val availability = ArCoreApk.getInstance().checkAvailability(context)
        return availability.isSupported
    }
}
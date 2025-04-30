package com.sid.data.data.usecase

import com.sid.data.data.repository.ArCoreRepository
import jakarta.inject.Inject

class CheckArCoreSupportUseCase @Inject constructor(
    private val arCoreRepository: ArCoreRepository
) {
    operator fun invoke(): Boolean {
        return arCoreRepository.isArCoreSupported()
    }
}
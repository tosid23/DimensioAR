package com.sid.home.data.usecase

import com.sid.data.data.entity.MeasurementEntity
import com.sid.home.data.repository.MeasurementRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllMeasurementsUseCase @Inject constructor(
    private val measurementRepository: MeasurementRepository
) {
    operator fun invoke(): Flow<List<MeasurementEntity>> {
        return measurementRepository.getAllMeasurements()
    }
}
package com.sid.data.data.usecase

import com.sid.data.data.entity.MeasurementEntity
import com.sid.data.data.repository.MeasurementRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllMeasurementsUseCase @Inject constructor(
    private val measurementRepository: MeasurementRepository
) {
    operator fun invoke(): Flow<List<MeasurementEntity>> {
        return measurementRepository.getAllMeasurements()
    }
}
package com.sid.data.data.repository

import com.sid.data.data.dao.MeasurementDao
import com.sid.data.data.entity.MeasurementEntity
import com.sid.data.data.entity.dummyMeasurements
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface MeasurementRepository {
    fun getAllMeasurements(): Flow<List<MeasurementEntity>>
}

class MeasurementRepositoryImpl @Inject constructor(
    private val measurementDao: MeasurementDao
) : MeasurementRepository {
    override fun getAllMeasurements(): Flow<List<MeasurementEntity>> = flow {
        val measurements = measurementDao.getAllMeasurements().ifEmpty { dummyMeasurements }
        emit(measurements)
    }
}
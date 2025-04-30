package com.sid.data.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sid.data.data.entity.MeasurementEntity

@Dao
interface MeasurementDao {
    @Insert
    suspend fun insert(measurement: MeasurementEntity)

    @Query("SELECT * FROM measurements")
    suspend fun getAllMeasurements(): List<MeasurementEntity>

}
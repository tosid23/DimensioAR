package com.sid.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sid.data.data.dao.MeasurementDao
import com.sid.data.data.entity.MeasurementEntity
import com.sid.data.data.converter.PointListConverter

@Database(entities = [MeasurementEntity::class], version = 1, exportSchema = false)
@TypeConverters(PointListConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun measurementDao(): MeasurementDao

    companion object {
        const val DATABASE_NAME = "app_database"
    }
}
package com.sid.data.data.entity

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.sid.data.data.entity.Point3D
import com.sid.data.data.converter.PointListConverter

@Keep
@Entity(tableName = "measurements")
@TypeConverters(PointListConverter::class)
data class MeasurementEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "timestamp")
    val timestamp: Long,

    @ColumnInfo(name = "measurement_type")
    val measurementType: String, // Store enum name, e.g., MeasurementType.DISTANCE.name

    @ColumnInfo(name = "result_value")
    val resultValue: Double,

    @ColumnInfo(name = "result_unit")
    val resultUnit: String, // Store enum name, e.g., DistanceUnit.METERS.name

    @ColumnInfo(name = "ar_points_json")
    val arPoints: List<Point3D>?,

    @ColumnInfo(name = "thumbnail_uri")
    val thumbnailUri: String? = null // Optional image reference
)

val dummyMeasurements = listOf(
    MeasurementEntity(
        name = "Measurement 1",
        timestamp = 1678886400,
        measurementType = "Distance",
        resultValue = 10.5,
        resultUnit = "meters",
        arPoints = null
    ),
    MeasurementEntity(
        name = "Measurement 2",
        timestamp = 1678972800,
        measurementType = "Height",
        resultValue = 1.8,
        resultUnit = "meters",
        arPoints = null
    ),
    MeasurementEntity(
        name = "Measurement 3",
        timestamp = 1679059200,
        measurementType = "Distance",
        resultValue = 5.2,
        resultUnit = "meters",
        arPoints = null
    ),
    MeasurementEntity(
        name = "Measurement 4",
        timestamp = 1679145600,
        measurementType = "Width",
        resultValue = 2.5,
        resultUnit = "meters",
        arPoints = null
    ),
    MeasurementEntity(
        name = "Measurement 5",
        timestamp = 1679232000,
        measurementType = "Distance",
        resultValue = 8.0,
        resultUnit = "meters",
        arPoints = null
    ),
)
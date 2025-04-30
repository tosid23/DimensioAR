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
package com.sid.data.data.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sid.data.data.entity.Point3D
import java.lang.reflect.Type

/**
 * Room TypeConverter to convert a List of Point3D objects to a JSON String
 * and back, allowing it to be stored in a single database column.
 *
 * Requires the Gson library: implementation("com.google.code.gson:gson:2.10.1")
 */
class PointListConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String?): List<Point3D>? {
        // Return null immediately if the stored value is null or blank
        if (value.isNullOrBlank()) {
            return null
        }
        // Define the specific generic type List<Point3D> for Gson
        val listType: Type = object : TypeToken<List<Point3D>>() {}.type
        return try {
            // Attempt to parse the JSON string into the List<Point3D>
            gson.fromJson(value, listType)
        } catch (e: Exception) {
            // Handle potential JSON parsing errors (e.g., malformed JSON)
            // Log the error for debugging purposes
            println("Error parsing Point3D list from JSON: ${e.message}")
            null // Return null indicating failure
        }
    }

    @TypeConverter
    fun fromList(list: List<Point3D>?): String? {
        // Return null if the list itself is null
        if (list == null) {
            return null
        }
        return try {
            // Attempt to serialize the list into a JSON string
            gson.toJson(list)
        } catch (e: Exception) {
            // Handle potential JSON serialization errors
            // Log the error for debugging purposes
            println("Error serializing Point3D list to JSON: ${e.message}")
            null // Return null indicating failure
        }
    }
}
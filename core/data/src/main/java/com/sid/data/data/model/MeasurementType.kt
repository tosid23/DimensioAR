package com.sid.data.data.model

enum class MeasurementType {
    /**
     * Measures the straight-line distance between two points.
     */
    DISTANCE,

    /**
     * Measures the length of a path defined by multiple connected points.
     */
    PATH_LENGTH,

    /**
     * Measures the area of a rectangle defined by specific points (e.g., 3 points for corners).
     * Note: The exact points needed might vary based on your implementation.
     */
    AREA_RECTANGLE,

    /**
     * Measures the area of a polygon defined by multiple points.
     */
    AREA_POLYGON,

    /**
     * Measures the radius or diameter of a circle identified in the AR scene.
     * You might split this into RADIUS and DIAMETER if needed.
     */
    CIRCLE,

    /**
     * Measures the volume of a box (cuboid) defined by points.
     */
    VOLUME_BOX,

    /**
     * Represents an unknown or unsupported measurement type.
     * Useful for error handling or future expansion.
     */
    UNKNOWN
}
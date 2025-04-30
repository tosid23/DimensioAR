package com.sid.data.data.model

enum class DistanceUnit {
    /**
     * Metric unit: Meters. Often the standard for ARCore/ARKit.
     */
    METERS,

    /**
     * Metric unit: Centimeters. Useful for smaller objects.
     */
    CENTIMETERS,

    /**
     * Metric unit: Millimeters. Useful for very precise measurements.
     */
    MILLIMETERS,

    /**
     * Imperial unit: Feet. Common in some regions.
     */
    FEET,

    /**
     * Imperial unit: Inches. Common in some regions.
     */
    INCHES,

    /**
     * Represents an unknown or unspecified unit.
     */
    UNKNOWN
}
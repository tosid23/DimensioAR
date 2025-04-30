package com.sid.data.data.entity

/**
 * Represents a point in 3D space, typically captured from an AR session.
 *
 * @property x The X-coordinate.
 * @property y The Y-coordinate.
 * @property z The Z-coordinate.
 */
data class Point3D(
    val x: Float,
    val y: Float,
    val z: Float
)
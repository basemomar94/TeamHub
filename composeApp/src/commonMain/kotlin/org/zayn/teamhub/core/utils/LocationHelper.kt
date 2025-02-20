package org.zayn.teamhub.core.utils

import org.zayn.teamhub.core.models.Location
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

expect suspend fun getCurrentLocation(): Location?

expect fun openMap(latitude: Double?, longitude: Double?)

expect fun isGpsAvailable(): Boolean

expect fun isLocationAllowed(): Boolean

fun haversineInMeters(workLocation: Location, attendanceLocation: Location): Double {
    val logger = Logger("haversineInMeters")
    logger.d("workLocation $workLocation   attendanceLocation $attendanceLocation")
    val R = 6371000.0 // Earth's radius in meters
    val workLat = workLocation.lat ?: return 0.0
    val workLon = workLocation.lon ?: return 0.0
    val attendanceLat = attendanceLocation.lat ?: return 0.0
    val attendanceLon = attendanceLocation.lon ?: return 0.0

    val dLat = (attendanceLat - workLat).toRadians()
    val dLon = (attendanceLon - workLon).toRadians()

    val a = sin(dLat / 2).pow(2) +
            cos(workLat.toRadians()) * cos(attendanceLat.toRadians()) * sin(dLon / 2).pow(2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))
    return R * c // Distance in meters
}

// Extension function for converting degrees to radians
fun Double.toRadians(): Double = this * (PI / 180)





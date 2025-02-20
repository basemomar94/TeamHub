package org.zayn.teamhub.core.models

enum class AttendanceMethod {
    MANUAL,           // User marks attendance manually
    ADMIN_ASSIGNED,   // Admin records attendance for the user
    AUTO_GEOFENCE,    // Automatically marked when user leaves the work location
    AUTO_TIME_BASED   // Automatically marked based on working hours (e.g., shift end)
}

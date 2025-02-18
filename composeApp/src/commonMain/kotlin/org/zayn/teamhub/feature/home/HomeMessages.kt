package org.zayn.teamhub.feature.home

sealed class HomeMessage {
    data class ApiError(val message: String) : HomeMessage()
    data object LocationNotAllowed : HomeMessage()
    data object GPSNotAllowed : HomeMessage()
    data object AttendanceRecorded : HomeMessage()
}
package org.zayn.teamhub.feature.home

sealed class RecordAttendanceError {
    data class ApiError(val message: String) : RecordAttendanceError()
    data object LocationNotAllowed : RecordAttendanceError()
    data object GPSNotAllowed : RecordAttendanceError()
    data object AttendanceRecorded : RecordAttendanceError()
}
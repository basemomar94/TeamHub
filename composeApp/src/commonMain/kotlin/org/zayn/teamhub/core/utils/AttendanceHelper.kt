package org.zayn.teamhub.core.utils

import org.zayn.teamhub.core.models.AttendanceFlag
import org.zayn.teamhub.core.models.AttendanceType
import org.zayn.teamhub.core.models.Company
import org.zayn.teamhub.core.models.Location
import org.zayn.teamhub.core.models.User
import org.zayn.teamhub.core.utils.Logger.Companion.createLogger

fun getAttendanceFlag(
    company: Company?,
    user: User?,
    attendanceLocation: Location?,
    attendanceTime: Long,
    attendanceDevice: String,
    type: AttendanceType
): List<String> {
    val flagsList = mutableListOf<String>()

    val distanceTolerance = company?.distanceTolerance ?: 0.0
    val lateTolerance = company?.lateTolerance ?: 0L
    val distanceBetweenWork =
        haversineInMeters(
            workLocation = Location(lat = company?.lat, lon = company?.lon),
            attendanceLocation = Location(
                lat = attendanceLocation?.lat,
                lon = attendanceLocation?.lon
            )
        )
    if (distanceBetweenWork > distanceTolerance) flagsList.add(AttendanceFlag.OUT_OF_LOCATION.name)
    if (attendanceDevice != user?.deviceName) flagsList.add(AttendanceFlag.UNAUTHORIZED_DEVICE.name)
    Logger.createLogger("distance").d("distance is $distanceBetweenWork")

    if (type == AttendanceType.CLOCK_IN) {
        if (lateTolerance < calculateTimeDifference(
                clockInTime = company?.clockInTime ?: "",
                userCheckInMillis = attendanceTime
            )
        ) flagsList.add(AttendanceFlag.LATE.name)
    }
    return flagsList


}
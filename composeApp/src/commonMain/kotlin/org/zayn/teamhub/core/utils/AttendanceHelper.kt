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

    company?.distanceTolerance ?: 0.0
    haversineInMeters(
        workLocation = Location(lat = company?.lat, lon = company?.lon),
        attendanceLocation = Location(
            lat = attendanceLocation?.lat,
            lon = attendanceLocation?.lon
        )
    )
    if (isOutOfLocation(
            companyLocation = Location(
                lat = company?.lat,
                lon = company?.lon
            ),
            attendanceLocation = attendanceLocation,
            distanceTolerance = company?.distanceTolerance
        )
    ) flagsList.add(AttendanceFlag.OUT_OF_LOCATION.name)
    if (attendanceDevice != user?.deviceName) flagsList.add(AttendanceFlag.UNAUTHORIZED_DEVICE.name)

    if (type == AttendanceType.CLOCK_IN) {
        //TODO need to fix the late logic
 /*       if (isLate(
                lateTolerance = company?.lateTolerance,
                companyStart = company?.clockInTime,
                attendanceClockIn = attendanceTime
            )
        ) flagsList.add(AttendanceFlag.LATE.name)*/
    }
    return flagsList
}

private fun isOutOfLocation(
    companyLocation: Location?,
    attendanceLocation: Location?,
    distanceTolerance: Double?
): Boolean {
    val distanceBetweenWork =
        haversineInMeters(
            workLocation = Location(lat = companyLocation?.lat, lon = companyLocation?.lon),
            attendanceLocation = Location(
                lat = attendanceLocation?.lat,
                lon = attendanceLocation?.lon
            )
        )
    return distanceBetweenWork > (distanceTolerance ?: 0.0)
}

private fun isLate(lateTolerance: Long?, companyStart: String?, attendanceClockIn: Long): Boolean {
    val timeDiff = calculateTimeDifference(
        clockInTime = companyStart ?: "",
        userCheckInMillis = attendanceClockIn
    )
    return if (lateTolerance != null) {
        lateTolerance < timeDiff
    } else false
}
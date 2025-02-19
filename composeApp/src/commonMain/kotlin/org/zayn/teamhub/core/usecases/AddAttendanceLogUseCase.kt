package org.zayn.teamhub.core.usecases

import org.zayn.teamhub.core.models.AttendanceType
import org.zayn.teamhub.core.repo.IAttendanceRepo

class AddAttendanceLogUseCase(
    private val attendanceRepo: IAttendanceRepo
) {
    suspend operator fun invoke(
        attendanceType: AttendanceType,
        lat: Double?,
        lon: Double?,
        userId: String
    ) =
        attendanceRepo.addAttendance(type = attendanceType, lat = lat, log = lon, userId = userId)
}
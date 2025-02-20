package org.zayn.teamhub.core.usecases

import org.zayn.teamhub.core.models.AttendanceFlag
import org.zayn.teamhub.core.models.AttendanceMethod
import org.zayn.teamhub.core.models.AttendanceType
import org.zayn.teamhub.core.repo.IAttendanceRepo

class AddAttendanceLogUseCase(
    private val attendanceRepo: IAttendanceRepo
) {
    suspend operator fun invoke(
        attendanceType: AttendanceType,
        lat: Double?,
        lon: Double?,
        userId: String,
        deviceName: String?,
        flag: List<String>,
        method: AttendanceMethod,
        time: Long
    ) =
        attendanceRepo.addAttendance(
            type = attendanceType,
            lat = lat,
            log = lon,
            userId = userId,
            deviceName = deviceName,
            flag = flag,
            method = method,
            time = time
        )
}
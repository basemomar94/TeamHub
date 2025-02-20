package org.zayn.teamhub.core.repo

import kotlinx.coroutines.flow.Flow
import org.zayn.teamhub.core.models.Attendance
import org.zayn.teamhub.core.models.AttendanceFlag
import org.zayn.teamhub.core.models.AttendanceMethod
import org.zayn.teamhub.core.models.AttendanceType
import org.zayn.teamhub.core.utils.networkresultwrapper.NetworkResult

interface IAttendanceRepo {

    suspend fun addAttendance(
        type: AttendanceType,
        lat: Double?,
        log: Double?,
        userId: String,
        deviceName: String?,
        flag: List<String>,
        time: Long,
        method: AttendanceMethod
    ): Flow<NetworkResult<String>>

    suspend fun getAttendanceByUser(
        userId: String,
        start: Long,
        end: Long,
    ): Flow<NetworkResult<List<Attendance>>>

    suspend fun getTodayWorkingHours(): Flow<NetworkResult<List<Attendance>>>
}
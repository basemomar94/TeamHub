package org.zayn.teamhub.core.repo

import kotlinx.coroutines.flow.Flow
import org.zayn.teamhub.core.models.AttendanceType
import org.zayn.teamhub.core.utils.networkresultwrapper.NetworkResult

interface IAttendanceRepo {

    suspend fun addAttendance(type: AttendanceType): Flow<NetworkResult<String>>
}
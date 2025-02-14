package org.zayn.teamhub.core.usecases

import kotlinx.coroutines.flow.Flow
import org.zayn.teamhub.core.models.AttendanceType
import org.zayn.teamhub.core.repo.IAttendanceRepo
import org.zayn.teamhub.core.repo.IUserRepo
import org.zayn.teamhub.core.utils.networkresultwrapper.NetworkResult

class AddAttendanceUseCase(private val userRepo: IUserRepo, private val attendanceRepo: IAttendanceRepo) {
    suspend operator fun invoke(attendanceType: AttendanceType): Flow<NetworkResult<String>> {
        val attendanceResult = attendanceRepo.addAttendance(attendanceType)
        val userResult = userRepo.addUserAttendance(attendanceType)
        return attendanceResult
    }
}
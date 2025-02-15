package org.zayn.teamhub.core.usecases

import org.zayn.teamhub.core.models.AttendanceType
import org.zayn.teamhub.core.repo.IUserRepo

class UpdateUserAttendanceUseCase(private val repo: IUserRepo) {

    suspend operator fun invoke(type: AttendanceType) = repo.addUserAttendance(type)
}
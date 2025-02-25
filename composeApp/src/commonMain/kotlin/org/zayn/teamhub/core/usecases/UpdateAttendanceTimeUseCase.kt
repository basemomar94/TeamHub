package org.zayn.teamhub.core.usecases

import org.zayn.teamhub.core.repo.IAttendanceRepo

class UpdateAttendanceTimeUseCase(private val repo: IAttendanceRepo) {

    suspend operator fun invoke(id: String, time: Long) = repo.updateAttendanceTime(id, time)
}
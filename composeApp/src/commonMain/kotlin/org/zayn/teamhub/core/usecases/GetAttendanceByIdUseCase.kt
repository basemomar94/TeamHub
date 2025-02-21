package org.zayn.teamhub.core.usecases

import org.zayn.teamhub.core.repo.IAttendanceRepo

class GetAttendanceByIdUseCase(private val repo: IAttendanceRepo) {

    suspend operator fun invoke(id: String) = repo.getAttendanceById(id)
}
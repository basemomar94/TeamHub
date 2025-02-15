package org.zayn.teamhub.core.usecases

import org.zayn.teamhub.core.repo.IAttendanceRepo

class GetAttendanceByUser(private val repo: IAttendanceRepo) {

    suspend operator fun invoke(userId: String) = repo.getAttendanceByUser(userId)
}
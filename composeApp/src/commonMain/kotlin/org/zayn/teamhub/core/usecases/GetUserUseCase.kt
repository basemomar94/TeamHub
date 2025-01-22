package org.zayn.teamhub.core.usecases

import org.zayn.teamhub.core.repo.IUserRepo

class GetUserUseCase(private val repo: IUserRepo) {

    suspend operator fun invoke(id: String) = repo.getUser(id)
}
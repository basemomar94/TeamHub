package org.zayn.teamhub.core.usecases

import org.zayn.teamhub.core.models.User
import org.zayn.teamhub.core.repo.IUserRepo

class AddNewUserUseCase(private val repo: IUserRepo) {

    suspend operator fun invoke(user: User) = repo.addNewUser(user)
}
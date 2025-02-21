package org.zayn.teamhub.core.usecases

import org.zayn.teamhub.core.models.UpdatedUser
import org.zayn.teamhub.core.repo.IUserRepo

class UpdateUserInfoUseCase(private val userRepo: IUserRepo) {

    suspend operator fun invoke(updatedUser: UpdatedUser) = userRepo.updateUser(updatedUser)
}
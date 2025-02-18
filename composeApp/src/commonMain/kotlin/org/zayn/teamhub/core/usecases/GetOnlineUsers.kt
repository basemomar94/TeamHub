package org.zayn.teamhub.core.usecases

import org.zayn.teamhub.core.repo.IUserRepo

class GetOnlineUsers(private val repo: IUserRepo) {
    suspend operator fun invoke() = repo.getOnlineUsers()
}
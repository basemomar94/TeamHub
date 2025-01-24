package org.zayn.teamhub.core.usecases

import dev.gitlive.firebase.auth.FirebaseAuth
import org.zayn.teamhub.core.repo.IUserRepo

class GetCurrentUserUseCase(
    private val auth: FirebaseAuth,
    private val userUseCase: GetUserUseCase
) {

    suspend operator fun invoke() = auth.currentUser?.uid?.let { id-> userUseCase(id) }

}
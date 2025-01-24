package org.zayn.teamhub.core.usecases

import dev.gitlive.firebase.auth.FirebaseAuth

class GetCurrentUserUseCase(
    private val auth: FirebaseAuth,
    private val userUseCase: GetUserUseCase
) {

    suspend operator fun invoke() = userUseCase(auth.currentUser?.uid ?: "")

}
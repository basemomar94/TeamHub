package org.zayn.teamhub.core.usecases

import org.zayn.teamhub.core.auth.AuthManager

class LogInUseCase(private val authManager: AuthManager) {
    suspend operator fun invoke(email: String, password: String) =
        authManager.signIn(mail = email, password = password)

}
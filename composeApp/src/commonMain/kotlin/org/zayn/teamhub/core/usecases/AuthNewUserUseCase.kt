package org.zayn.teamhub.core.usecases

import org.zayn.teamhub.core.auth.IAuthManager
import org.zayn.teamhub.core.models.User

class AuthNewUserUseCase(private val authManager: IAuthManager) {

    suspend operator fun invoke(user: User) =
        authManager.signUp(mail = user.email, password = user.password)
}
package org.zayn.teamhub.feature.auth

actual class AuthManager {
    actual suspend fun signIn(mail: String, password: String): Boolean {
        TODO("Not yet implemented")
    }

    actual fun signOut() {
    }

    actual fun getUserId(): String? {
        TODO("Not yet implemented")
    }
}
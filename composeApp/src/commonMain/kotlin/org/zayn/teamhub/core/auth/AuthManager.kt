package org.zayn.teamhub.core.auth

import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.FirebaseUser

class AuthManager(private val auth: FirebaseAuth) : IAuth {
    override suspend fun signIn(mail: String, password: String): Boolean {
        return try {
            val authResult =
                auth.signInWithEmailAndPassword(email = mail, password = password)
            authResult.user != null
        } catch (e: Exception) {

            false
        }
    }

    override suspend fun signOut() {
        auth.signOut()
    }

    override suspend fun getCurrentUserId(): FirebaseUser? {
        return auth.currentUser
    }
}
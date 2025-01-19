package org.zayn.teamhub.core.auth

import dev.gitlive.firebase.auth.FirebaseUser

interface IAuth {
    suspend fun signIn(mail: String, password: String): Boolean
    suspend fun signOut()
    suspend fun getCurrentUserId(): FirebaseUser?
}
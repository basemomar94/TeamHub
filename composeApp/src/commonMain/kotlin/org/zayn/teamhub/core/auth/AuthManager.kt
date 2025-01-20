package org.zayn.teamhub.core.auth

import dev.gitlive.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import org.zayn.teamhub.core.utils.networkresultwrapper.NetworkResult

class AuthManager(private val auth: FirebaseAuth) : IAuthManager {
    override suspend fun signIn(mail: String, password: String) =
        flow {
            try {
                val authResult = auth.signInWithEmailAndPassword(email = mail, password = password)
                val isSignedIn = authResult.user != null
                emit(NetworkResult.Success(isSignedIn))
            } catch (e: Exception) {
                emit(NetworkResult.Failure(e.message ?: "Unknown error occurred"))
            }
        }.catch { e ->
            emit(NetworkResult.Failure(e.message ?: "Unexpected error occurred"))
        }

    override suspend fun signOut() {
        auth.signOut()
    }
}
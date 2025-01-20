package org.zayn.teamhub.core.auth

import dev.gitlive.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import org.zayn.teamhub.core.utils.networkresultwrapper.NetworkResult

interface IAuthManager {
    suspend fun signIn(mail: String, password: String): Flow<NetworkResult<Boolean>>
    suspend fun signOut()
}
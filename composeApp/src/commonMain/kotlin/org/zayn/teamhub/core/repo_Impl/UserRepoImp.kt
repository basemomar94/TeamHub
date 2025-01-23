package org.zayn.teamhub.core.repo_Impl

import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.Source
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.zayn.teamhub.core.models.User
import org.zayn.teamhub.core.repo.IUserRepo
import org.zayn.teamhub.core.utils.FirebaseCollections
import org.zayn.teamhub.core.utils.networkresultwrapper.NetworkResult


class UserRepoImp(private val firestore: FirebaseFirestore) : IUserRepo {
    override suspend fun getUser(id: String) = flow {
        try {
            val response = firestore.collection(FirebaseCollections.USER_COLLECTION).document(id)
                .get(Source.DEFAULT).data<User>()
            emit(NetworkResult.Success(response))

        } catch (e: Exception) {
            emit(NetworkResult.Failure(e.message ?: "Unknown error"))

        }
    }.catch { e ->
        emit(NetworkResult.Failure(e.message ?: "Unknown error"))

    }.flowOn(Dispatchers.IO)
}
package org.zayn.teamhub.core.repo_Impl

import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import org.zayn.teamhub.core.base.BaseRepo
import org.zayn.teamhub.core.models.User
import org.zayn.teamhub.core.repo.IUserRepo
import org.zayn.teamhub.core.utils.CollectionReference
import org.zayn.teamhub.core.utils.FirebaseCollections
import org.zayn.teamhub.core.utils.networkresultwrapper.NetworkResult


class UserRepoImp(private val firestore: FirebaseFirestore) : BaseRepo(), IUserRepo {

    override suspend fun getUser(id: String): Flow<NetworkResult<User>> {
        return firestore.fetchDocumentAsFlow<User>(
            collection = FirebaseCollections.USER_COLLECTION,
            documentId = id,
            operationName = "Fetching User"
        )
    }

    override suspend fun getAllCompanyUsers(companyId: String): Flow<NetworkResult<List<User>>> {
        return firestore.fetchQueryAsFlow<User>(
            collection = FirebaseCollections.USER_COLLECTION,
            queryBuilder = { this.where { CollectionReference.CompanyId equalTo companyId } },
            operationName = "Fetching Company Users"
        )
    }

    override suspend fun addNewUser(user: User): Flow<NetworkResult<String>> {
        return firestore.addDocumentAsFlow(
            collection = FirebaseCollections.USER_COLLECTION,
            data = user,
            documentId = user.id,
            "Add new user"
        )
    }
}
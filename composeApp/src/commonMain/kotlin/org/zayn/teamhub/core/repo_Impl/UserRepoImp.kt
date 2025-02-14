package org.zayn.teamhub.core.repo_Impl

import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import org.zayn.teamhub.core.base.BaseRepo
import org.zayn.teamhub.core.models.AttendanceType
import org.zayn.teamhub.core.models.User
import org.zayn.teamhub.core.repo.IUserRepo
import org.zayn.teamhub.core.utils.CollectionReference
import org.zayn.teamhub.core.utils.FirebaseCollections
import org.zayn.teamhub.core.utils.getCurrentTime
import org.zayn.teamhub.core.utils.networkresultwrapper.NetworkResult


class UserRepoImp(private val firestore: FirebaseFirestore, private val auth: FirebaseAuth) :
    BaseRepo(), IUserRepo {

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
            queryBuilder = { this.where { CollectionReference.COMPANY_ID equalTo companyId } },
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

    override suspend fun addUserAttendance(type: AttendanceType): Flow<NetworkResult<Boolean>> {
        val updates = mapOf(
            CollectionReference.CURRENT_STATUS to type.name,
            CollectionReference.LAST_UPDATE to getCurrentTime()
        )
        return firestore.updateDocumentAsFlow<User>(
            collection = FirebaseCollections.USER_COLLECTION,
            documentId = auth.currentUser?.uid ?: "",
            updates = updates
        )
    }
}
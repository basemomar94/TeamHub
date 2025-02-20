package org.zayn.teamhub.core.repo_Impl

import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import org.zayn.teamhub.core.base.BaseRepo
import org.zayn.teamhub.core.models.Company
import org.zayn.teamhub.core.repo.ICompanyRepo
import org.zayn.teamhub.core.utils.FirebaseCollections.COMPANY_COLLECTION
import org.zayn.teamhub.core.utils.networkresultwrapper.NetworkResult

class CompanyRepo(private val firestore: FirebaseFirestore) : BaseRepo(), ICompanyRepo {

    override suspend fun getCompanyById(id: String): Flow<NetworkResult<Company>> {
        return firestore.fetchDocumentAsFlow(
            collection = COMPANY_COLLECTION,
            documentId = id,
            operationName = "getCompanyById"
        )
    }
}
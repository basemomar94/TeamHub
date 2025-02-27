package org.zayn.teamhub.core.repo_Impl

import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import org.zayn.teamhub.core.base.BaseRepo
import org.zayn.teamhub.core.models.AppSettings
import org.zayn.teamhub.core.repo.ISettingsRepo
import org.zayn.teamhub.core.utils.CollectionReference
import org.zayn.teamhub.core.utils.FirebaseCollections
import org.zayn.teamhub.core.utils.networkresultwrapper.NetworkResult

class SettingsRepoImp(private val firestore: FirebaseFirestore) : BaseRepo(), ISettingsRepo {

    override suspend fun getAppSettings(): Flow<NetworkResult<AppSettings>> {
        return firestore.fetchDocumentAsFlow(
            collection = FirebaseCollections.SETTINGS_COLLECTION,
            documentId = CollectionReference.APP,
            operationName = "getAppSettings"
        )
    }

}
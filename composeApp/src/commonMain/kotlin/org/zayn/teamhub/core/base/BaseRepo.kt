package org.zayn.teamhub.core.base

import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.Query
import dev.gitlive.firebase.firestore.Source
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.zayn.teamhub.core.utils.Logger
import org.zayn.teamhub.core.utils.Logger.Companion.createLogger
import org.zayn.teamhub.core.utils.networkresultwrapper.NetworkResult

open class BaseRepo {

    protected inline fun <reified T> FirebaseFirestore.fetchDocumentAsFlow(
        collection: String,
        documentId: String,
        source: Source = Source.DEFAULT,
        operationName: String = "Fetching Document"
    ): Flow<NetworkResult<T>> {
        return runFireStoreOperationAsFlow(operationName) {
            val document = collection(collection).document(documentId).get(source)
            document.data<T>()
        }
    }

    protected inline fun <reified T> FirebaseFirestore.fetchQueryAsFlow(
        collection: String,
        crossinline queryBuilder: Query.() -> Query = { this },
        source: Source = Source.DEFAULT,
        operationName: String = "Fetching Query"
    ): Flow<NetworkResult<List<T>>> {
        return runFireStoreOperationAsFlow(operationName) {
            val query = queryBuilder(collection(collection))
            val snapshot = query.get(source)
            snapshot.documents.mapNotNull { it.data<T>() }
        }
    }

    protected inline fun <reified T : Any> FirebaseFirestore.addDocumentAsFlow(
        collection: String,
        data: T,
        operationName: String = "Adding Document"
    ): Flow<NetworkResult<String>> {
        return runFireStoreOperationAsFlow(operationName) {
            val documentRef = collection(collection).add(data)
            documentRef.id
        }
    }

    protected inline fun <reified T> FirebaseFirestore.updateDocumentAsFlow(
        collection: String,
        documentId: String,
        updates: Map<String, Any>,
        operationName: String = "Updating Document"
    ): Flow<NetworkResult<Boolean>> {
        return runFireStoreOperationAsFlow(operationName) {
            collection(collection).document(documentId).update(updates)
            true
        }
    }


    protected inline fun <T> runFireStoreOperationAsFlow(
        operationName: String,
        crossinline operation: suspend () -> T?
    ): Flow<NetworkResult<T>> = flow {
        val logger = Logger.createLogger("BaseRepo")
        try {
            logger.i("$operationName: Starting operation...")
            val result = operation()
            if (result != null) {
                logger.i("$operationName: Operation successful")
                emit(NetworkResult.Success(result))
            } else {
                val errorMsg = "$operationName: No data found or operation returned null"
                logger.e(errorMsg)
                emit(NetworkResult.Failure(errorMsg))
            }
        } catch (e: Exception) {
            val errorMsg = "$operationName: Error occurred - ${e.message}"
            logger.e(errorMsg)
            emit(NetworkResult.Failure(errorMsg))
        }
    }.catch { e ->
        val logger = Logger.createLogger("BaseRepo")
        val errorMsg = "$operationName: Unhandled error - ${e.message}"
        logger.e(errorMsg)
        emit(NetworkResult.Failure(e.message ?: "Unknown error"))
    }.flowOn(Dispatchers.IO)


}
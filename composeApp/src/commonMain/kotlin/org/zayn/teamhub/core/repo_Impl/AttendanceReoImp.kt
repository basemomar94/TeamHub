package org.zayn.teamhub.core.repo_Impl

import dev.gitlive.firebase.firestore.Direction
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import org.zayn.teamhub.core.base.BaseRepo
import org.zayn.teamhub.core.models.Attendance
import org.zayn.teamhub.core.models.AttendanceMethod
import org.zayn.teamhub.core.models.AttendanceType
import org.zayn.teamhub.core.repo.IAttendanceRepo
import org.zayn.teamhub.core.utils.CollectionReference
import org.zayn.teamhub.core.utils.FirebaseCollections
import org.zayn.teamhub.core.utils.Logger
import org.zayn.teamhub.core.utils.Logger.Companion.createLogger
import org.zayn.teamhub.core.utils.networkresultwrapper.NetworkResult

class AttendanceReoImp(
    private val firestore: FirebaseFirestore,
) : BaseRepo(), IAttendanceRepo {
    override suspend fun addAttendance(
        type: AttendanceType,
        lat: Double?,
        log: Double?,
        userId: String,
        deviceName: String?,
        flag: List<String>,
        time: Long,
        method: AttendanceMethod,
    ): Flow<NetworkResult<String>> {
        val documentId = time.toString()
        val attendance =
            Attendance(
                lat = lat,
                long = log,
                userId = userId,
                createdAt = time,
                type = type.name,
                method = method.name,
                flag = flag,
                deviceName = deviceName,
                id = documentId
            )
        return firestore.addDocumentAsFlow(
            collection = FirebaseCollections.ATTENDANCE_COLLECTION,
            data = attendance,
            documentId = documentId,
            operationName = "addAttendance"
        )
    }

    override suspend fun getAttendanceByUser(
        userId: String,
        start: Long,
        end: Long,
    ): Flow<NetworkResult<List<Attendance>>> {
        Logger.createLogger("getAttendanceByUser").d("start $start   end $end for userid $userId")
        return firestore.fetchQueryAsFlow(
            collection = FirebaseCollections.ATTENDANCE_COLLECTION,
            queryBuilder = {
                this.where { CollectionReference.USER_ID equalTo userId }
                    .where { CollectionReference.CREATED_AT greaterThanOrEqualTo start }
                    .where { CollectionReference.CREATED_AT lessThanOrEqualTo end }
                    .orderBy(CollectionReference.CREATED_AT, Direction.DESCENDING)

            },
            operationName = "getAttendanceByUser"
        )
    }

    override suspend fun getAttendanceById(id: String): Flow<NetworkResult<Attendance>> {
        return firestore.fetchDocumentAsFlow(
            collection = FirebaseCollections.ATTENDANCE_COLLECTION,
            documentId = id
        )
    }

    override suspend fun updateAttendanceTime(
        id: String,
        createdAt: Long
    ): Flow<NetworkResult<Boolean>> {
        val updates = mapOf(
            CollectionReference.CREATED_AT to createdAt,
            CollectionReference.IS_EDITED to true
        )
        return firestore.updateDocumentAsFlow<Attendance>(
            collection = FirebaseCollections.ATTENDANCE_COLLECTION,
            documentId = id,
            updates = updates
        )
    }
}
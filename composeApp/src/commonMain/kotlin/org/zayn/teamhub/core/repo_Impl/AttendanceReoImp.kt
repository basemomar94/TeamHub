package org.zayn.teamhub.core.repo_Impl

import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.firestore.Direction
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import org.zayn.teamhub.core.base.BaseRepo
import org.zayn.teamhub.core.models.Attendance
import org.zayn.teamhub.core.models.AttendanceFlag
import org.zayn.teamhub.core.models.AttendanceMethod
import org.zayn.teamhub.core.models.AttendanceType
import org.zayn.teamhub.core.repo.IAttendanceRepo
import org.zayn.teamhub.core.utils.CollectionReference
import org.zayn.teamhub.core.utils.FirebaseCollections
import org.zayn.teamhub.core.utils.Logger
import org.zayn.teamhub.core.utils.Logger.Companion.createLogger
import org.zayn.teamhub.core.utils.getCurrentTime
import org.zayn.teamhub.core.utils.networkresultwrapper.NetworkResult

class AttendanceReoImp(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
) : BaseRepo(), IAttendanceRepo {
    override suspend fun addAttendance(
        type: AttendanceType,
        lat: Double?,
        log: Double?,
        userId: String,
        deviceName: String?,
        flag: List<String>,
        time:Long,
        method: AttendanceMethod,
    ): Flow<NetworkResult<String>> {
        val attendance =
            Attendance(
                lat = lat,
                long = log,
                userId = userId,
                createdAt =time,
                type = type.name,
                method = method.name,
                flag = flag,
                deviceName = deviceName
            )
        return firestore.addDocumentAsFlow(
            collection = FirebaseCollections.ATTENDANCE_COLLECTION,
            data = attendance,
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

    override suspend fun getTodayWorkingHours(): Flow<NetworkResult<List<Attendance>>> {
        val userId = auth.currentUser?.uid ?: ""
        return firestore.fetchQueryAsFlow(
            collection = FirebaseCollections.ATTENDANCE_COLLECTION,
            queryBuilder = { this.where { CollectionReference.USER_ID equalTo userId } },
            operationName = "getAttendanceByUser"
        )
    }
}
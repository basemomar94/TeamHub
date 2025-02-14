package org.zayn.teamhub.core.repo_Impl

import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import org.zayn.teamhub.core.auth.AuthManager
import org.zayn.teamhub.core.base.BaseRepo
import org.zayn.teamhub.core.models.Attendance
import org.zayn.teamhub.core.models.AttendanceType
import org.zayn.teamhub.core.repo.IAttendanceRepo
import org.zayn.teamhub.core.utils.FirebaseCollections
import org.zayn.teamhub.core.utils.getCurrentTime
import org.zayn.teamhub.core.utils.networkresultwrapper.NetworkResult

class AttendanceReoImp(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : BaseRepo(), IAttendanceRepo {
    override suspend fun addAttendance(type: AttendanceType): Flow<NetworkResult<String>> {
        val attendance =
            Attendance(
                userId = auth.currentUser?.uid ?: "N/A",
                createdAt = getCurrentTime(),
                type = type.name
            )
        return firestore.addDocumentAsFlow(
            collection = FirebaseCollections.ATTENDANCE_COLLECTION,
            data = attendance,
            operationName = "addAttendance"
        )
    }
}
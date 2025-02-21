package org.zayn.teamhub.core.repo

import kotlinx.coroutines.flow.Flow
import org.zayn.teamhub.core.models.AttendanceType
import org.zayn.teamhub.core.models.UpdatedUser
import org.zayn.teamhub.core.models.User
import org.zayn.teamhub.core.utils.networkresultwrapper.NetworkResult

interface IUserRepo {
    suspend fun getUser(id: String): Flow<NetworkResult<User>>
    suspend fun getAllCompanyUsers(companyId: String): Flow<NetworkResult<List<User>>>
    suspend fun addNewUser(user: User): Flow<NetworkResult<String>>
    suspend fun addUserAttendance(
        type: AttendanceType,
        userId: String
    ): Flow<NetworkResult<Boolean>>

    suspend fun getOnlineUsers(): Flow<NetworkResult<List<User>>>

    suspend fun updateUser(updatedUser: UpdatedUser): Flow<NetworkResult<Boolean>>

}
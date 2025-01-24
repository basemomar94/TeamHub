package org.zayn.teamhub.core.repo

import kotlinx.coroutines.flow.Flow
import org.zayn.teamhub.core.models.User
import org.zayn.teamhub.core.utils.networkresultwrapper.NetworkResult

interface IUserRepo {
    suspend fun getUser(id: String): Flow<NetworkResult<User>>
    suspend fun getAllCompanyUsers(companyId: String): Flow<NetworkResult<List<User>>>
    suspend fun addNewUser(user: User): Flow<NetworkResult<Boolean>>
}
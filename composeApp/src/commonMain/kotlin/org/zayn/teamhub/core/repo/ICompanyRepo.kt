package org.zayn.teamhub.core.repo

import kotlinx.coroutines.flow.Flow
import org.zayn.teamhub.core.models.Company
import org.zayn.teamhub.core.utils.networkresultwrapper.NetworkResult

interface ICompanyRepo {

    suspend fun getCompanyById(id: String): Flow<NetworkResult<Company>>
}
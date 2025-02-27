package org.zayn.teamhub.core.repo

import kotlinx.coroutines.flow.Flow
import org.zayn.teamhub.core.models.AppSettings
import org.zayn.teamhub.core.utils.networkresultwrapper.NetworkResult

interface ISettingsRepo {

    suspend fun getAppSettings(): Flow<NetworkResult<AppSettings>>
}
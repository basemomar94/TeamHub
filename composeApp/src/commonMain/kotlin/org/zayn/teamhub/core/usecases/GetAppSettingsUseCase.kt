package org.zayn.teamhub.core.usecases

import org.zayn.teamhub.core.repo.ISettingsRepo

class GetAppSettingsUseCase(private val repo: ISettingsRepo) {

    suspend operator fun invoke() = repo.getAppSettings()
}
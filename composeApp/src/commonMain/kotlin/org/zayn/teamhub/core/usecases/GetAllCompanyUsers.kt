package org.zayn.teamhub.core.usecases

import org.zayn.teamhub.core.repo.IUserRepo

class GetAllCompanyUsers(private val repo: IUserRepo) {

    suspend operator fun invoke(companyId: String) = repo.getAllCompanyUsers(companyId)
}
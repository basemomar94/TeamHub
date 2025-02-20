package org.zayn.teamhub.core.usecases

import org.zayn.teamhub.core.repo.ICompanyRepo

class GetCompanyByIdUseCase(private val repo: ICompanyRepo) {

    suspend operator fun invoke(id: String) = repo.getCompanyById(id)
}
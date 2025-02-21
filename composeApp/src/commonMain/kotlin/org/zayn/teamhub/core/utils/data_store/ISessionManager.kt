package org.zayn.teamhub.core.utils.data_store

import org.zayn.teamhub.core.models.Company
import org.zayn.teamhub.core.models.Roles
import org.zayn.teamhub.core.models.User

interface ISessionManager {

    fun putUser(user: User)
    fun getUser(): User?

    fun putCompany(company: Company)
    fun getCompany(): Company?
}
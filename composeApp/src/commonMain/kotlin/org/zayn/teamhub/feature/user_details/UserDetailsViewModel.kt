package org.zayn.teamhub.feature.user_details

import androidx.lifecycle.ViewModel
import org.zayn.teamhub.core.models.Roles
import org.zayn.teamhub.core.models.Session
import org.zayn.teamhub.core.utils.data_store.ISessionManager
import org.zayn.teamhub.core.utils.enumValueOrNullOf

class UserDetailsViewModel(private val sessionManager: ISessionManager) : ViewModel() {

    val userRoleTxt = sessionManager.getUser()?.role
    val isAdmin = enumValueOrNullOf<Roles>(userRoleTxt) == Roles.ADMIN
}
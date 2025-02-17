package org.zayn.teamhub.feature.profile

import androidx.lifecycle.ViewModel
import org.zayn.teamhub.core.auth.AuthManager
import org.zayn.teamhub.core.base.BaseViewModel

class ProfileViewModel(private val authManager: AuthManager) :
    BaseViewModel<ProfileState, ProfileEvent, ProfileEffect>() {

    private suspend fun logOut() {
        authManager.signOut()
    }

    override fun setInitialState(): ProfileState {
        return ProfileState.Ideal
    }

    override suspend fun handleEvents(event: ProfileEvent) {
        when (event) {
            ProfileEvent.OnLogoutClicked -> logOut()
        }
    }
}
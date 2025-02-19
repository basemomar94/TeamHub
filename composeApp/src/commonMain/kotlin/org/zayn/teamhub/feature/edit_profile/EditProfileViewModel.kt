package org.zayn.teamhub.feature.edit_profile

import org.zayn.teamhub.core.base.BaseViewModel
import org.zayn.teamhub.core.utils.data_store.ISessionManager

class EditProfileViewModel(private val session: ISessionManager) :
    BaseViewModel<EditProfileState, EditProfileEvent, EditProfileEffect>() {
    val user = session.getUser()

    override fun setInitialState(): EditProfileState {
        return EditProfileState.Ideal
    }

    override suspend fun handleEvents(event: EditProfileEvent) {
        TODO("Not yet implemented")
    }
}
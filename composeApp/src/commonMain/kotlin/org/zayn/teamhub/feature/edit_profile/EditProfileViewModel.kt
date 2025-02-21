package org.zayn.teamhub.feature.edit_profile

import org.zayn.teamhub.core.base.BaseViewModel
import org.zayn.teamhub.core.models.UpdatedUser
import org.zayn.teamhub.core.models.User
import org.zayn.teamhub.core.usecases.UpdateUserInfoUseCase
import org.zayn.teamhub.core.utils.data_store.ISessionManager

class EditProfileViewModel(
    session: ISessionManager,
    private val updateUserInfoUseCase: UpdateUserInfoUseCase
) :
    BaseViewModel<EditProfileState, EditProfileEvent, EditProfileEffect>() {
    val user = session.getUser()

    override fun setInitialState(): EditProfileState {
        return EditProfileState.Ideal
    }

    override suspend fun handleEvents(event: EditProfileEvent) {
        when (event) {
            is EditProfileEvent.UpdateProfile -> updateUserData(event.user)
        }
    }

    private suspend fun updateUserData(user: UpdatedUser) {
        launchAndCollectResult(
            flow = updateUserInfoUseCase(user),
            tag = "updateUserData",
            onStart = { setState { EditProfileState.Loading } },
            onComplete = { setEffect { EditProfileEffect.Navigate } }
        )

    }
}
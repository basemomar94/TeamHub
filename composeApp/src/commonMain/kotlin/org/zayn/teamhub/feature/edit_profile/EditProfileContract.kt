package org.zayn.teamhub.feature.edit_profile

import org.zayn.teamhub.core.base.ViewEvent
import org.zayn.teamhub.core.base.ViewSideEffect
import org.zayn.teamhub.core.base.ViewState
import org.zayn.teamhub.core.models.UpdatedUser

sealed class EditProfileState : ViewState {
    data object Ideal : EditProfileState()
    data object Loading : EditProfileState()
}

sealed class EditProfileEvent : ViewEvent {
    data class UpdateProfile(val user: UpdatedUser) : EditProfileEvent()

}

sealed class EditProfileEffect : ViewSideEffect {
    data object Navigate : EditProfileEffect()
    data class ShowMessage(val message: String) : EditProfileEffect()

}
package org.zayn.teamhub.feature.profile.ui

import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel
import org.zayn.teamhub.feature.profile.ProfileEvent
import org.zayn.teamhub.feature.profile.ProfileViewModel

@Composable
fun ProfileScreen(viewModel: ProfileViewModel = koinViewModel(), onClick: (ProfileItem) -> Unit) {
    ProfileItemsList {
        if (it.action == ProfileAction.LOG_OUT) {
            viewModel.setEvent(ProfileEvent.OnLogoutClicked)
        }
        onClick(it)
    }
}
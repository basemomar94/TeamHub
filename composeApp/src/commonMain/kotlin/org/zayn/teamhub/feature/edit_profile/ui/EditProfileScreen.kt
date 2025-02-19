package org.zayn.teamhub.feature.edit_profile.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.compose.viewmodel.koinViewModel
import org.zayn.teamhub.core.desgin_repo.LoadingIndicator
import org.zayn.teamhub.feature.edit_profile.EditProfileState
import org.zayn.teamhub.feature.edit_profile.EditProfileViewModel

@Composable
fun EditProfileScreen(viewModel: EditProfileViewModel = koinViewModel()) {
    val state by viewModel.viewState.collectAsState()
    when (state) {
        EditProfileState.Ideal -> viewModel.user?.let { EditProfileCompose(user = it) }
        EditProfileState.Loading -> LoadingIndicator()
    }

}
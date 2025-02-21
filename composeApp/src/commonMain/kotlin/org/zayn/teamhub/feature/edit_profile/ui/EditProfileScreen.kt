package org.zayn.teamhub.feature.edit_profile.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import org.zayn.teamhub.core.base.SideEffectsKey
import org.zayn.teamhub.core.desgin_repo.LoadingIndicator
import org.zayn.teamhub.feature.edit_profile.EditProfileEffect
import org.zayn.teamhub.feature.edit_profile.EditProfileEvent
import org.zayn.teamhub.feature.edit_profile.EditProfileState
import org.zayn.teamhub.feature.edit_profile.EditProfileViewModel
import org.zayn.teamhub.feature.home.HomeSideEffect
import org.zayn.teamhub.feature.home.RecordAttendanceError

@Composable
fun EditProfileScreen(viewModel: EditProfileViewModel = koinViewModel(), onUpdate: () -> Unit) {
    val state by viewModel.viewState.collectAsState()
    LaunchedEffect(SideEffectsKey) {
        viewModel.effect.onEach { effect ->
            when (effect) {
                EditProfileEffect.Navigate -> onUpdate()
                is EditProfileEffect.ShowMessage -> TODO()
            }
        }.collectLatest { }
    }

    when (state) {
        EditProfileState.Ideal -> viewModel.user?.let {
            EditProfileCompose(user = it, onUpdate = onUpdate) { updatedUser ->
                viewModel.setEvent(EditProfileEvent.UpdateProfile(updatedUser))
            }
        }

        EditProfileState.Loading -> LoadingIndicator()
    }

}
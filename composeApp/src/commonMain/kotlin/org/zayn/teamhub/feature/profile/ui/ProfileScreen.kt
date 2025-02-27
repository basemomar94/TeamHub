package org.zayn.teamhub.feature.profile.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel
import org.zayn.teamhub.core.utils.getAppVersion
import org.zayn.teamhub.core.utils.getBuildVariant
import org.zayn.teamhub.feature.profile.ProfileEvent
import org.zayn.teamhub.feature.profile.ProfileViewModel

@Composable
fun ProfileScreen(viewModel: ProfileViewModel = koinViewModel(), onClick: (ProfileItem) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        ProfileItemsList {
            if (it.action == ProfileAction.LOG_OUT) {
                viewModel.setEvent(ProfileEvent.OnLogoutClicked)
            }
            onClick(it)
        }
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "version" + getAppVersion(),
            style = MaterialTheme.typography.bodySmall
        )
    }

}
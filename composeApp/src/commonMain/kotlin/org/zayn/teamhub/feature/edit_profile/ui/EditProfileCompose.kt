package org.zayn.teamhub.feature.edit_profile.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.zayn.teamhub.core.desgin_repo.PhoneEditText
import org.zayn.teamhub.core.models.User

@Composable
fun EditProfileCompose(user: User, modifier: Modifier = Modifier) {
    var phoneNumber by remember { mutableStateOf(user.phoneNumber) }
    Column(modifier = modifier, verticalArrangement = Arrangement.SpaceBetween) {
        ProfileHeader(
            userImage = user.profileImage,
            userName = user.firstName.orEmpty() + " " + user.lastName.orEmpty()
        ) {

        }
        PhoneEditText(
            phone = phoneNumber,
            onPhoneChange = { phoneNumber = it }
        )
    }
}
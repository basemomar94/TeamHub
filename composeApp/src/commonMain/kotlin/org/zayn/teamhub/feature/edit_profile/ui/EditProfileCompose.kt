package org.zayn.teamhub.feature.edit_profile.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.zayn.teamhub.core.desgin_repo.CustomTextField
import org.zayn.teamhub.core.desgin_repo.DefaultButton
import org.zayn.teamhub.core.desgin_repo.MailEditText
import org.zayn.teamhub.core.desgin_repo.PhoneEditText
import org.zayn.teamhub.core.desgin_repo.Vspacer
import org.zayn.teamhub.core.models.UpdatedUser
import org.zayn.teamhub.core.models.User
import teamhub.composeapp.generated.resources.Res
import teamhub.composeapp.generated.resources.save

@Composable
fun EditProfileCompose(
    user: User,
    modifier: Modifier = Modifier,
    onUpdate: () -> Unit,
    onSaveClick: (UpdatedUser) -> Unit,
) {
    var phoneNumber by remember { mutableStateOf(user.phoneNumber) }
    var email by remember { mutableStateOf(user.email) }
    var firstName by remember { mutableStateOf(user.firstName) }
    var lastName by remember { mutableStateOf(user.lastName) }
    Column(modifier = modifier.padding(16.dp), verticalArrangement = Arrangement.SpaceBetween) {
        firstName?.let {
            CustomTextField(query = it, onQueryChanged = { firstName = it })
        }
        Vspacer(8.dp)
        lastName?.let {
            CustomTextField(query = it, onQueryChanged = { lastName = it })
        }
        Vspacer(8.dp)
        email?.let {
            MailEditText(mail = it, onMailChange = { email = it })
        }
        Vspacer(8.dp)
        PhoneEditText(phone = phoneNumber, onPhoneChange = { phoneNumber = it })
        Vspacer(16.dp)
        DefaultButton(
            text = stringResource(Res.string.save),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            val updatedUser = UpdatedUser(
                phoneNumber = phoneNumber,
                email = email,
                firstName = firstName,
                lastName = lastName,
                userId = user.id
            )
            onSaveClick(updatedUser)
        }

    }
}
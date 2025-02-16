package org.zayn.teamhub.feature.admin.add_new_user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.zayn.teamhub.core.desgin_repo.CustomTextField
import org.zayn.teamhub.core.desgin_repo.MailEditText
import org.zayn.teamhub.core.desgin_repo.PasswordEditText
import org.zayn.teamhub.core.models.User
import org.zayn.teamhub.core.utils.getCurrentTime

@Composable
fun SignupCompose(
    modifier: Modifier = Modifier,
    onCreateUser: (User) -> Unit,

    ) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var companyId by remember { mutableStateOf("") }
    var isAdmin by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CustomTextField(
            query = firstName,
            placeHolder = "First Name",
            onQueryChanged = { firstName = it }
        )

        CustomTextField(
            query = lastName,
            placeHolder = "Last Name",
            onQueryChanged = { lastName = it }
        )

        MailEditText(
            mail = email,
            onMailChange = { email = it }
        )

        CustomTextField(
            query = companyId,
            placeHolder = "Company ID",
            onQueryChanged = { companyId = it }
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Is Admin?")
            Spacer(modifier = Modifier.width(8.dp))
            Switch(
                checked = isAdmin,
                onCheckedChange = { isAdmin = it }
            )
        }

        PasswordEditText(
            password = password,
            onPasswordChange = { password = it }
        )

        Button(
            onClick = {
                val newUser = User(
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    companyId = companyId,
                    isAdmin = isAdmin,
                    password = password,
                    createdAt = getCurrentTime()
                )
                onCreateUser(newUser)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Create User")
        }
    }
}

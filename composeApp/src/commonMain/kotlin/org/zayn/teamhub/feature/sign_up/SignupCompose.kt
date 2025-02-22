package org.zayn.teamhub.feature.sign_up

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
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
import org.zayn.teamhub.core.desgin_repo.PasswordEditText
import org.zayn.teamhub.core.models.Roles
import org.zayn.teamhub.core.models.User
import org.zayn.teamhub.core.utils.getCurrentTime
import teamhub.composeapp.generated.resources.Res
import teamhub.composeapp.generated.resources.company_id
import teamhub.composeapp.generated.resources.create_user
import teamhub.composeapp.generated.resources.enter_password
import teamhub.composeapp.generated.resources.first_name
import teamhub.composeapp.generated.resources.is_admin
import teamhub.composeapp.generated.resources.last_name
import teamhub.composeapp.generated.resources.sign_up

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
            placeHolder = stringResource(Res.string.first_name),
            onQueryChanged = { firstName = it },
            startIcon = Icons.Default.Person
        )

        CustomTextField(
            query = lastName,
            placeHolder = stringResource(Res.string.last_name),
            onQueryChanged = { lastName = it },
            startIcon = Icons.Default.Person
        )

        MailEditText(
            mail = email,
            onMailChange = { email = it }
        )

        CustomTextField(
            query = companyId,
            placeHolder = stringResource(Res.string.company_id),
            onQueryChanged = { companyId = it },
            startIcon = Icons.Default.Info
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(Res.string.is_admin))
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

        DefaultButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(Res.string.sign_up)
        ) {
            val newUser = User(
                firstName = firstName,
                lastName = lastName,
                email = email,
                companyId = companyId,
                role = if (isAdmin) Roles.ADMIN.name else Roles.USER.name,
                password = password,
                createdAt = getCurrentTime()
            )
            onCreateUser(newUser)

        }
    }
}

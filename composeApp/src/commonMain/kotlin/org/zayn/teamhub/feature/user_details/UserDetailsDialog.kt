package org.zayn.teamhub.feature.user_details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Brands
import compose.icons.fontawesomeicons.brands.Whatsapp
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.zayn.teamhub.core.desgin_repo.DefaultButton
import org.zayn.teamhub.core.desgin_repo.Vspacer
import org.zayn.teamhub.core.models.User
import org.zayn.teamhub.core.utils.dialContact
import org.zayn.teamhub.core.utils.sendEmail
import org.zayn.teamhub.core.utils.sendWhatsapp
import org.zayn.teamhub.core.utils.toLocalizedDateTime
import org.zayn.teamhub.feature.home.ui.UserCircularItem
import teamhub.composeapp.generated.resources.Res
import teamhub.composeapp.generated.resources.edit_profile
import teamhub.composeapp.generated.resources.last_update
import teamhub.composeapp.generated.resources.user_attendance
import teamhub.composeapp.generated.resources.user_info

@Composable
fun UserDetailsDialog(
    viewModel: UserDetailsViewModel = koinViewModel(),
    user: User,
    onAttendanceClick: (String?) -> Unit,
    onEditInfoClick: (User) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
            ) {

                IconButton(onClick = { onDismiss() }, modifier = Modifier.align(Alignment.End)) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier.padding(4.dp)
                    )
                }
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = stringResource(Res.string.user_info),
                    style = MaterialTheme.typography.subtitle1
                )

                Vspacer(8.dp)

                UserCircularItem(
                    user = user, modifier = Modifier.align(Alignment.CenterHorizontally),
                )

                Vspacer(8.dp)

                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "${user.firstName.orEmpty()} ${user.lastName.orEmpty()}",
                    style = MaterialTheme.typography.body1
                )
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = stringResource(Res.string.last_update) + user.lastUpdate.toLocalizedDateTime(),
                    style = MaterialTheme.typography.caption
                )

                Vspacer(8.dp)

                user.email?.let { email ->
                    Text(text = email,
                        fontSize = 16.sp,
                        color = MaterialTheme.colors.secondary,
                        modifier = Modifier.clickable { sendEmail(email) })
                }

                Vspacer(8.dp)

                user.phoneNumber?.let { phone ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = phone, fontSize = 16.sp, modifier = Modifier.weight(1f))

                        Spacer(modifier = Modifier.width(16.dp))

                        IconButton(onClick = { dialContact(phone) }) {
                            Icon(
                                imageVector = Icons.Default.Call, modifier = Modifier.size(24.dp),
                                contentDescription = "Call"
                            )
                        }

                        IconButton(onClick = { sendWhatsapp(phone) }) {
                            Icon(
                                imageVector = FontAwesomeIcons.Brands.Whatsapp,
                                modifier = Modifier.size(24.dp),
                                contentDescription = "WhatsApp"
                            )
                        }
                    }
                }

                if (viewModel.isAdmin) {
                    DefaultButton(
                        text = stringResource(Res.string.user_attendance),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        onAttendanceClick(user.id)

                    }
                    Vspacer(8.dp)
                    DefaultButton(
                        text = stringResource(Res.string.edit_profile),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        onEditInfoClick(user)

                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}


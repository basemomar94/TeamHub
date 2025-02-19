package org.zayn.teamhub.feature.user_details

import androidx.compose.runtime.Composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import org.zayn.teamhub.core.desgin_repo.Vspacer
import org.zayn.teamhub.core.models.User
import org.zayn.teamhub.core.utils.dialContact
import org.zayn.teamhub.core.utils.sendEmail
import org.zayn.teamhub.core.utils.sendWhatsapp
import org.zayn.teamhub.feature.home.ui.UserCircularItem

@Composable
fun UserDetailsSheet(user: User, onDismiss: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Expanded,
        skipHalfExpanded = true
    )
    LaunchedEffect(Unit) {
        coroutineScope.launch { sheetState.show() }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "close",
                    modifier = Modifier.align(Alignment.End).clickable {
                        coroutineScope.launch { sheetState.hide() }
                            .invokeOnCompletion { onDismiss() }
                    }
                )
                UserCircularItem(user = user, modifier = Modifier.align(Alignment.CenterHorizontally))
                Vspacer(8.dp)
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "${user.firstName.orEmpty()} ${user.lastName.orEmpty()}",
                    style = MaterialTheme.typography.subtitle1
                )

                Vspacer(16.dp)

                user.email?.let { email ->
                    Text(
                        text = email,
                        fontSize = 16.sp,
                        color = MaterialTheme.colors.secondary,
                        modifier = Modifier.clickable {
                            sendEmail(email)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                user.phoneNumber?.let { phone ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = phone, fontSize = 16.sp)

                        Spacer(modifier = Modifier.width(16.dp))

                        IconButton(onClick = {
                            dialContact(phone)
                        }) {
                            Icon(imageVector = Icons.Default.Call, contentDescription = "Call")
                        }

                        // WhatsApp Icon
                        IconButton(onClick = {
                            sendWhatsapp(phone)
                        }) {
                            Icon(imageVector = Icons.Default.Check, contentDescription = "WhatsApp")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

            }
        }
    ) {}
}

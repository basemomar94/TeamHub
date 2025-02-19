package org.zayn.teamhub.feature.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.zayn.teamhub.core.desgin_repo.Vspacer
import org.zayn.teamhub.core.models.User
import org.zayn.teamhub.feature.user_details.UserDetailsSheet
import teamhub.composeapp.generated.resources.Res
import teamhub.composeapp.generated.resources.online_now

@Composable
fun OnlineUserGrid(users: List<User>) {
    var selectedUser by remember<MutableState<User?>> { mutableStateOf(null) }


    Card(elevation = 4.dp, shape = RoundedCornerShape(12.dp), modifier = Modifier.padding(8.dp)) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                style = MaterialTheme.typography.body1,
                text = stringResource(Res.string.online_now),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Vspacer(8.dp)
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 60.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(users) { user ->
                    UserItem(user = user) { clickedUser ->
                        {selectedUser = clickedUser}
                    }
                }
            }
        }
    }
    if (selectedUser != null) {
        UserDetailsSheet(selectedUser!!) {
            selectedUser = null
        }
    }

}

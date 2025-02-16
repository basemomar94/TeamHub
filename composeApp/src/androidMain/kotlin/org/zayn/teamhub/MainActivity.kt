package org.zayn.teamhub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import dev.icerock.moko.resources.StringResource
import org.zayn.teamhub.core.navigation.App
import org.zayn.teamhub.SharedRes.strings
import org.zayn.teamhub.core.utils.AppContext
import org.zayn.teamhub.core.utils.getString

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }
}


@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
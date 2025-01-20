package org.zayn.teamhub

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.zayn.teamhub.core.navigation.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "TeamHub",
    ) {
        App()
    }
}
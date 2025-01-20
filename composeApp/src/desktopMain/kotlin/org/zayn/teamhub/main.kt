package org.zayn.teamhub

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.koin.core.context.startKoin
import org.zayn.teamhub.core.di.sharedModule
import org.zayn.teamhub.core.navigation.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "TeamHub",
    ) {
        App()
        startKoin {
            modules(sharedModule)
        }
    }
}
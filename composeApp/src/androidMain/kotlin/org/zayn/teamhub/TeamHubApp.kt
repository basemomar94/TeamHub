package org.zayn.teamhub

import android.app.Application
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.initialize
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

import org.zayn.teamhub.core.di.sharedModule


class TeamHubApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TeamHubApp)
            modules(sharedModule)
        }

    }
}
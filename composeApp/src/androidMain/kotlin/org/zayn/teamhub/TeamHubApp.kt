package org.zayn.teamhub

import android.app.Application
import android.content.Context
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.initialize
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

import org.zayn.teamhub.core.di.sharedModule
import org.zayn.teamhub.core.utils.AppContext


class TeamHubApp : Application() {
    companion object {
        lateinit var appContext: Context
    }
    override fun onCreate() {
        super.onCreate()
        AppContext.apply { set(applicationContext) }
        startKoin {
            androidContext(this@TeamHubApp)
            modules(sharedModule)
        }

    }
}
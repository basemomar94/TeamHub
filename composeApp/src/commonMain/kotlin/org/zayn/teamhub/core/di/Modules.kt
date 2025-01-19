package org.zayn.teamhub.core.di

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
import org.koin.core.module.Module
import org.koin.dsl.module
import org.zayn.teamhub.core.auth.AuthManager

val sharedModule: Module = module {
    single<FirebaseAuth> {
        Firebase.auth
    }
    single { AuthManager(get()) }
}
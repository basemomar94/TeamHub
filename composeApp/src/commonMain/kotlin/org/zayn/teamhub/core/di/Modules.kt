package org.zayn.teamhub.core.di

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.zayn.teamhub.core.auth.AuthManager
import org.zayn.teamhub.core.auth.IAuthManager
import org.zayn.teamhub.core.usecases.LogInUseCase
import org.zayn.teamhub.feature.signIn.SignInViewModel


val sharedModule: Module = module {
    single<FirebaseAuth> { Firebase.auth }
    singleOf(::AuthManager) { bind<IAuthManager>() }
    factory { LogInUseCase(get()) }
    viewModelOf(::SignInViewModel)
}

package org.zayn.teamhub.core.di

import com.russhwolf.settings.Settings
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.zayn.teamhub.core.auth.AuthManager
import org.zayn.teamhub.core.auth.IAuthManager
import org.zayn.teamhub.core.repo.IUserRepo
import org.zayn.teamhub.core.repo_Impl.UserRepoImp
import org.zayn.teamhub.core.services.ISessionManager
import org.zayn.teamhub.core.services.ISharedPref
import org.zayn.teamhub.core.services.SessionManager
import org.zayn.teamhub.core.services.SharedPref
import org.zayn.teamhub.core.usecases.GetUserUseCase
import org.zayn.teamhub.core.usecases.LogInUseCase
import org.zayn.teamhub.feature.signIn.SignInViewModel


private val repoModules = module {
    singleOf(::AuthManager) { bind<IAuthManager>() }
 //   single<Settings> { PlatformSettings.Factory().create("shared_prefs") }

    singleOf(::SharedPref) { bind<ISharedPref>() }
    single<IUserRepo> { UserRepoImp(get()) }
}

private val viewModelsModules = module {
    viewModelOf(::SignInViewModel)

}

private val useCasesModules = module {
    factory { LogInUseCase(get()) }
    factory { GetUserUseCase(get()) }
}

private val firebaseModules = module {
    single<FirebaseAuth> { Firebase.auth }
    single { Firebase.firestore }
}

private val utilsModules = module {
    single { Settings }
    singleOf(::SessionManager) { bind<ISessionManager>() }

}

val sharedModule: Module = module {
    includes(
        repoModules,
        viewModelsModules,
        useCasesModules,
        firebaseModules,
        utilsModules
    )
}

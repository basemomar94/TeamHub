package org.zayn.teamhub.core.di

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
import org.zayn.teamhub.core.services.SessionManager
import org.zayn.teamhub.core.usecases.AddNewUserUseCase
import org.zayn.teamhub.core.usecases.AuthNewUserUseCase
import org.zayn.teamhub.core.usecases.GetAllCompanyUsers
import org.zayn.teamhub.core.usecases.GetCurrentUserUseCase
import org.zayn.teamhub.core.usecases.GetUserUseCase
import org.zayn.teamhub.core.usecases.LogInUseCase
import org.zayn.teamhub.feature.home.HomeViewModel
import org.zayn.teamhub.feature.signIn.SignInViewModel
import org.zayn.teamhub.feature.admin.usersList.presentation.UserListViewModel
import org.zayn.teamhub.feature.admin.add_new_user.AddUserViewModel


private val repoModules = module {
    singleOf(::AuthManager) { bind<IAuthManager>() }
    //   single<Settings> { PlatformSettings.Factory().create("shared_prefs") }

    single<IUserRepo> { UserRepoImp(get()) }
}

private val viewModelsModules = module {
    viewModelOf(::SignInViewModel)
    factory { SessionManager() }
    viewModelOf(::HomeViewModel)
    viewModelOf(::UserListViewModel)
    viewModelOf(::AddUserViewModel)
}

private val useCasesModules = module {
    factory { LogInUseCase(get()) }
    factory { GetUserUseCase(get()) }
    factory { GetAllCompanyUsers(get()) }
    factory { GetCurrentUserUseCase(get(), get()) }
    factory { AddNewUserUseCase(get()) }
    factory { AuthNewUserUseCase(get()) }
}

private val firebaseModules = module {
    single<FirebaseAuth> { Firebase.auth }
    single { Firebase.firestore }
}

private val utilsModules = module {
    //  single { SessionManager() }

}

val sharedModule: Module = module {
    includes(
        repoModules,
        useCasesModules,
        firebaseModules,
        utilsModules,
        viewModelsModules
    )
}

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
import org.zayn.teamhub.core.repo.IAttendanceRepo
import org.zayn.teamhub.core.repo.IUserRepo
import org.zayn.teamhub.core.repo_Impl.AttendanceReoImp
import org.zayn.teamhub.core.repo_Impl.UserRepoImp
import org.zayn.teamhub.core.usecases.AddAttendanceLogUseCase
import org.zayn.teamhub.core.usecases.AddNewUserUseCase
import org.zayn.teamhub.core.usecases.AuthNewUserUseCase
import org.zayn.teamhub.core.usecases.GetAllCompanyUsers
import org.zayn.teamhub.core.usecases.GetAttendanceByUser
import org.zayn.teamhub.core.usecases.GetCurrentUserUseCase
import org.zayn.teamhub.core.usecases.GetOnlineUsers
import org.zayn.teamhub.core.usecases.GetUserUseCase
import org.zayn.teamhub.core.usecases.LogInUseCase
import org.zayn.teamhub.core.usecases.UpdateUserAttendanceUseCase
import org.zayn.teamhub.core.utils.data_store.ISessionManager
import org.zayn.teamhub.core.utils.data_store.ISharedPrefManager
import org.zayn.teamhub.core.utils.data_store.SessionManager
import org.zayn.teamhub.core.utils.data_store.SharedPrefManager
import org.zayn.teamhub.feature.home.HomeViewModel
import org.zayn.teamhub.feature.signIn.SignInViewModel
import org.zayn.teamhub.feature.usersList.presentation.UserListViewModel
import org.zayn.teamhub.feature.sign_up.SignupViewModel
import org.zayn.teamhub.feature.work_summary.WorkSummaryViewModel
import org.zayn.teamhub.feature.work_day_details.WorkSessionViewModel
import org.zayn.teamhub.feature.profile.ProfileViewModel


private val repoModules = module {
    singleOf(::AuthManager) { bind<IAuthManager>() }
    single<IUserRepo> { UserRepoImp(get(), get()) }
    single<IAttendanceRepo> { AttendanceReoImp(get(), get()) }
}

private val viewModelsModules = module {
    viewModelOf(::SignInViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::UserListViewModel)
    viewModelOf(::SignupViewModel)
    viewModelOf(::WorkSummaryViewModel)
    viewModelOf(::WorkSessionViewModel)
    viewModelOf(::ProfileViewModel)
}

private val useCasesModules = module {
    factory { LogInUseCase(get()) }
    factory { GetUserUseCase(get()) }
    factory { GetAllCompanyUsers(get()) }
    factory { GetCurrentUserUseCase(get(), get()) }
    factory { AddNewUserUseCase(get()) }
    factory { AuthNewUserUseCase(get()) }
    factory { AddAttendanceLogUseCase(get()) }
    factory { UpdateUserAttendanceUseCase(get()) }
    factory { GetAttendanceByUser(get()) }
    factory { GetOnlineUsers(get()) }
}

private val firebaseModules = module {
    single<FirebaseAuth> { Firebase.auth }
    single { Firebase.firestore }
}

private val utilsModules = module {
    single<ISharedPrefManager> { SharedPrefManager() }
    single<ISessionManager> { SessionManager(get()) }
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

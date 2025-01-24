package org.zayn.teamhub.feature.home

import org.zayn.teamhub.core.base.BaseViewModel
import org.zayn.teamhub.core.services.SessionManager

class HomeViewModel(private val sessionManager: SessionManager) :
    BaseViewModel<HomeState, HomeEvent, HomeSideEffect>() {
    val userId = sessionManager.getUserId()

    override fun setInitialState(): HomeState {
       return HomeState.UnIntialized
    }

    override suspend fun handleEvents(event: HomeEvent) {
        TODO("Not yet implemented")
    }

    private fun getUser() {

    }
}
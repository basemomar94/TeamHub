package org.zayn.teamhub

import org.zayn.teamhub.core.base.BaseViewModel

class MainViewModel : BaseViewModel<MainState, MainEvent, MainEffect>() {
    override fun setInitialState(): MainState {
        return MainState.Ideal
    }

    override suspend fun handleEvents(event: MainEvent) {
        when (event) {
            MainEvent.GetSessionData -> {}
        }
    }
}
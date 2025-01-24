package org.zayn.teamhub.feature.home

import org.zayn.teamhub.core.base.ViewEvent
import org.zayn.teamhub.core.base.ViewSideEffect
import org.zayn.teamhub.core.base.ViewState

sealed class HomeState() : ViewState {
 data object UnIntialized:HomeState()
}

sealed class HomeEvent() : ViewEvent {

}

sealed class HomeSideEffect() : ViewSideEffect {

}
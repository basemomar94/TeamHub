package org.zayn.teamhub.feature.session_details

import org.zayn.teamhub.core.base.BaseViewModel
import org.zayn.teamhub.core.usecases.GetAttendanceByIdUseCase
import org.zayn.teamhub.core.utils.networkresultwrapper.NetworkResult

class SessionDetailsViewModel(private val getAttendanceByIdUseCase: GetAttendanceByIdUseCase) :
    BaseViewModel<SessionDetailsState, SessionDetailsEvent, SessionDetailsEffect>() {
    override fun setInitialState(): SessionDetailsState {
        return SessionDetailsState.Ideal
    }

    override suspend fun handleEvents(event: SessionDetailsEvent) {
        when (event) {
            is SessionDetailsEvent.GetAttendance -> getAttendance(event.id)

        }
    }

    private suspend fun getAttendance(id: String) {
        launchAndCollectResult(
            onStart = { setState { SessionDetailsState.Loading } },
            flow = getAttendanceByIdUseCase(id),
            tag = "getAttendance $id",
            resultSuccess = { networkResult ->
                if (networkResult is NetworkResult.Success) {
                    networkResult.data?.let {
                        setState { SessionDetailsState.LoadData(it) }
                    }
                }
            },
            onError = {
                setEffect {
                    SessionDetailsEffect.ShowSnackBar(
                        it.body?.message ?: "Fail"
                    )
                }
            },
            resultFailure = { setEffect { SessionDetailsEffect.ShowSnackBar(it.error) } }

        )

    }
}
package org.zayn.teamhub.feature.session_details

import org.zayn.teamhub.core.base.BaseViewModel
import org.zayn.teamhub.core.usecases.GetAttendanceByIdUseCase
import org.zayn.teamhub.core.usecases.UpdateAttendanceTimeUseCase
import org.zayn.teamhub.core.utils.networkresultwrapper.NetworkResult

class SessionDetailsViewModel(
    private val getAttendanceByIdUseCase: GetAttendanceByIdUseCase,
    private val updateAttendanceTimeUseCase: UpdateAttendanceTimeUseCase
) :
    BaseViewModel<SessionDetailsState, SessionDetailsEvent, SessionDetailsEffect>() {
    override fun setInitialState(): SessionDetailsState {
        return SessionDetailsState.Ideal
    }

    override suspend fun handleEvents(event: SessionDetailsEvent) {
        when (event) {
            is SessionDetailsEvent.GetAttendance -> getAttendance(event.id)
            is SessionDetailsEvent.UpdateAttendance -> updateAttendance(event.id, event.time)
        }
    }

    private suspend fun updateAttendance(id: String, time: Long) {
        launchAndCollectResult(
            flow = updateAttendanceTimeUseCase(id = id, time = time),
            tag = "updateAttendance",
            resultSuccess = {
                setEffect {
                    SessionDetailsEffect.ShowSnackBar("Successfully updated")
                    SessionDetailsEffect.Navigate
                }
            },
            resultFailure = {
                setEffect {
                    SessionDetailsEffect.ShowSnackBar(it.error)
                }
            }
        )
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
package org.zayn.teamhub.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.zayn.teamhub.core.utils.Logger.Companion.createLogger
import org.zayn.teamhub.core.utils.networkresultwrapper.ErrorBody
import org.zayn.teamhub.core.utils.networkresultwrapper.ErrorResponse
import org.zayn.teamhub.core.utils.networkresultwrapper.NetworkResult
import kotlin.coroutines.coroutineContext


/**
 * BaseViewModel is an abstract class designed to manage UI state, events, and side effects
 *
 * @param UiState Represents the state of the UI. Must implement the [ViewState] interface.
 * @param Event Encapsulates user or system-driven events. Must implement the [ViewEvent] interface.
 * @param Effect Represents one-off side effects like navigation or toast messages. Must implement the [ViewSideEffect] interface.
 */
abstract class BaseViewModel<UiState : ViewState, Event : ViewEvent, Effect : ViewSideEffect> :
    ViewModel() {

    private val logger = createLogger()

    /**
     * Defines the initial state of the ViewModel.
     * @return The initial state of the UI.
     */
    abstract fun setInitialState(): UiState

    /**
     * Handles events emitted to the ViewModel.
     * Must be implemented in subclasses to handle specific [Event]s and update the state or trigger effects.
     * @param event The event to handle.
     */
    abstract suspend fun handleEvents(event: Event)
    private val initialState: UiState by lazy { setInitialState() }

    private val _viewState: MutableStateFlow<UiState> = MutableStateFlow(initialState)

    /**
     * Publicly exposed [StateFlow] for observing the current UI state.
     */
    val viewState: StateFlow<UiState> = _viewState.asStateFlow()

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()

    private val _effect: Channel<Effect> = Channel()

    /**
     * Publicly exposed Flow for observing side effects.
     */
    val effect = _effect.receiveAsFlow()

    init {
        subscribeToEvents()
    }

    private fun subscribeToEvents() {
        viewModelScope.launch {
            _event.collect {
                handleEvents(it)
            }
        }
    }

    /**
     * Emits an event to the ViewModel.
     * This function is typically called from the UI layer to communicate user actions or external events.
     *
     * @param event The event to emit.
     */
    fun setEvent(event: Event) {
        viewModelScope.launch { _event.emit(event) }
    }

    /**
     * Updates the UI state using a reducer function.
     * The reducer takes the current state and returns a new state.
     *
     * @param reducer A lambda function that transforms the current state into a new state.
     */
    protected fun setState(reducer: UiState.() -> UiState) {
        val newState = viewState.value.reducer()
        _viewState.value = newState
    }

    /**
     * Launches a coroutine in the `viewModelScope` to collect results from a [Flow], handling various states like success, failure, and errors.
     *
     * @param flow The [Flow] to be collected.
     * @param resultSuccess Callback for handling successful results. Default is an empty lambda.
     * @param resultFailure Callback for handling failure results of type [NetworkResult.Failure]. Default is an empty lambda.
     * @param onError Callback for handling exceptions during flow collection. Default is an empty lambda.
     * @param onStart Callback invoked when the flow collection starts. Default is an empty lambda.
     * @param onComplete Callback invoked when the flow collection completes, with an optional [Throwable] cause. Default is an empty lambda.
     * @param tag Tag used for logging. Default is "Unknown".
     *
     * @return A [Job] representing the coroutine collecting the flow.
     *
     * Example:
     * ```
     * launchAndCollectResult(
     *     flow = someFlow,
     *     resultSuccess = { data -> handleSuccess(data) },
     *     onError = { error -> showError(error) },
     *     onStart = { showLoading() },
     *     onComplete = { hideLoading() },
     *     tag = "MyFlowTag"
     * )
     * ```
     */

    fun <T> launchAndCollectResult(
        flow: Flow<T>,
        resultSuccess: (T) -> Unit = {},
        resultFailure: (NetworkResult.Failure) -> Unit = {},
        onError: (ErrorResponse) -> Unit = {},
        onStart: () -> Unit = {},
        onComplete: (Throwable?) -> Unit = {},
        tag: String="Unknown",
    ): Job {
        logger.d("launchAndCollectResult called for flow with tag: $tag")

        return viewModelScope.launch {
            flow.flowOn(
                Dispatchers.IO
            ).onStart {
                onStart()
                logger.d("Flow with tag $tag started.")
            }.onCompletion { cause ->
                onComplete(cause)
                logger.d("Flow with tag $tag completed. Cause: ${cause?.message ?: "No cause"}")
            }.catch { exception ->
                logger.e("Exception in flow with tag $tag.", exception)
                onError(
                    ErrorResponse(
                        body = ErrorBody(
                            message = exception.message,
                            errors = null
                        ), code = null
                    )
                )
            }.safeCollect { result ->
                if (result is NetworkResult.Failure) {
                    logger.e("Failure result from flow with tag $tag: $result")
                    resultFailure(result)
                } else {
                    logger.i("Success result from flow with tag $tag: $result")
                    resultSuccess(result)
                }
            }
        }
    }

    private suspend inline fun <T> Flow<T>.safeCollect(crossinline action: suspend (T) -> Unit) {
        collect {
            coroutineContext.ensureActive()
            action(it)
        }
    }
}
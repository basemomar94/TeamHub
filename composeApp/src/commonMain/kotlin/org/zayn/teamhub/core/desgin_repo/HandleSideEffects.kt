package org.zayn.teamhub.core.desgin_repo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun <T> HandleSideEffects(
    effectFlow: Flow<T>,
    onEffect: suspend (T) -> Unit,
) {
    LaunchedEffect(effectFlow) {
        effectFlow.collectLatest { effect ->
            onEffect(effect)
        }
    }
}

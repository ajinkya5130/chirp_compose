package com.plcoding.core.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * Observes a Flow and executes an action for each emitted event in a lifecycle-aware manner.
 *
 * This composable function collects events from a Flow only when the lifecycle is in the STARTED state,
 * preventing unnecessary processing when the UI is not visible. Events are handled on the Main dispatcher.
 *
 * @param T The type of events emitted by the flow
 * @param flow The Flow to observe for events
 * @param key1 Optional key to restart the observation when changed
 * @param key2 Optional second key to restart the observation when changed
 * @param onEvent The suspend function to execute for each emitted event
 */
@Composable
fun <T> ObserveAsEvent(
    flow: Flow<T>,
    key1: Any? = null,
    key2: Any? = null,
    onEvent: suspend (T) -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner, key1, key2) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                flow.collect(onEvent)
            }
        }
    }

}
package com.plcoding.chirp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.core.domain.auth.ISessionDataStorage
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val sessionDataStorage: ISessionDataStorage,
) : ViewModel() {
    private val _state = MutableStateFlow(MainState())
    private var hasLoadedInitialData = false
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                observeSession()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = MainState()
        )


    private val eventChannel = Channel<MainEvent>()
    val event = eventChannel.receiveAsFlow()


    var previousRefreshTokenValue: String? = null

    init {
        viewModelScope.launch {
            val authInfo = sessionDataStorage.getAuthInfo().firstOrNull()
            _state.update {
                it.copy(
                    isCheckingAuth = false,
                    isLoggedIn = authInfo != null
                )
            }
        }
    }

    fun observeSession() {
        sessionDataStorage
            .getAuthInfo()
            .onEach { authValue ->
                val currentRefreshTokenValue = authValue?.refreshToken
                val isSessionExpired =
                    previousRefreshTokenValue != null && currentRefreshTokenValue == null
                if (isSessionExpired) {
                    sessionDataStorage.clearAuthInfo()
                    _state.update {
                        it.copy(
                            isLoggedIn = false
                        )
                    }
                    eventChannel.send(MainEvent.OnSessionExpired)
                }

                previousRefreshTokenValue = currentRefreshTokenValue

            }
            .launchIn(viewModelScope)
    }

}
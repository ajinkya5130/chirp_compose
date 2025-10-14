/**
Created by ajinkya on 11/10/25
 */
package com.plcoding.auth.presentation.register.register_success

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.auth.presentation.utils.Utility.KEY_EMAIL_STRING
import com.plcoding.core.domain.auth.IAuthService
import com.plcoding.core.domain.utils.onFailure
import com.plcoding.core.domain.utils.onSuccess
import com.plcoding.core.presentation.utils.toUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterSuccessViewModel(
    private val authService: IAuthService,
    saveStateHandle: SavedStateHandle,
) : ViewModel() {

    private var hasLoadedInitialData = false
    private val email = saveStateHandle.get<String>(KEY_EMAIL_STRING)
        ?: throw IllegalStateException("Email is required")

    private val eventChannel = Channel<RegisterSuccessEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(RegisterSuccessState(email))
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = RegisterSuccessState()
        )

    fun onAction(action: RegisterSuccessAction) {
        when (action) {
            RegisterSuccessAction.OnLoginClick -> Unit
            RegisterSuccessAction.OnResendClick -> resendVerificationEmail()
        }
    }

    private fun resendVerificationEmail() {
        if (state.value.isResendEnabled) return

        viewModelScope.launch {
            _state.update {
                it.copy(
                    isResendEnabled = true,
                )
            }
            authService.resendVerificationEmail(email)
                .onSuccess {
                    _state.update {
                        it.copy(
                            isResendEnabled = false,
                        )
                    }
                    eventChannel.send(RegisterSuccessEvent.ResendVerificationEmailSuccess)
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            isResendEnabled = false,
                            resnetErrorText = error.toUiText()
                        )
                    }
                }
        }
    }
}
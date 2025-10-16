/**
Created by ajinkya on 15/10/25
 */
package com.plcoding.auth.presentation.email_verification

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.auth.presentation.utils.Utility.KEY_TOKEN_STRING
import com.plcoding.core.domain.auth.IAuthService
import com.plcoding.core.domain.utils.onFailure
import com.plcoding.core.domain.utils.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EmailVerificationViewModel(
    private val authService: IAuthService,
    saveStateHandle: SavedStateHandle,
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val token = saveStateHandle.get<String>(KEY_TOKEN_STRING)

    private val _state = MutableStateFlow(EmailVerificationScreenState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                verifyEmail()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = EmailVerificationScreenState()
        )

    fun verifyEmail() {
        _state.update {
            it.copy(
                isVerifying = true,
            )
        }
        viewModelScope.launch {
            authService.verifyEmail(token ?: "Token not found").onSuccess {
                _state.update {
                    it.copy(
                        isVerifying = false,
                        isVerified = true,
                    )
                }
            }.onFailure {
                _state.update {
                    it.copy(
                        isVerifying = false,
                        isVerified = false,
                    )
                }
            }
        }
    }

    fun onAction(action: EmailVerificationScreenAction) = Unit

}
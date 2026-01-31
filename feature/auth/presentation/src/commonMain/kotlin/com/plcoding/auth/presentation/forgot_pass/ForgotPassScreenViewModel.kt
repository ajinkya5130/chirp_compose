/**
Created by ajinkya on 25/10/25
 */
package com.plcoding.auth.presentation.forgot_pass

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.auth.domain.utils.EmailValidator
import com.plcoding.core.domain.auth.IAuthService
import com.plcoding.core.domain.utils.onFailure
import com.plcoding.core.domain.utils.onSuccess
import com.plcoding.core.presentation.utils.toUiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ForgotPassScreenViewModel(
    private val authService: IAuthService,
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(ForgotPassScreenState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                observeValidation()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ForgotPassScreenState()
        )

    fun onAction(action: ForgotPassScreenAction) {
        when (action) {
            ForgotPassScreenAction.OnSubmitClick -> sendResetPasswordEmail()
        }
    }


    private val isEmailValidFlow = snapshotFlow {
        state.value.emailTextFieldState.text.toString()
    }.map {
        EmailValidator.validate(it)
    }.distinctUntilChanged()

    private fun observeValidation() {
        isEmailValidFlow.onEach { isEmailValid ->
            _state.update {
                it.copy(
                    canSubmit = isEmailValid
                )
            }
        }.launchIn(viewModelScope)

    }

    private fun sendResetPasswordEmail() {
        if (state.value.isLoading && !state.value.canSubmit) {
            return
        }

        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    isEmailSendSuccessfully = false,
                    errorText = null
                )
            }
            val email = state.value.emailTextFieldState.text.toString()
            authService
                .forgotPassword(email)
                .onSuccess {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isEmailSendSuccessfully = true
                        )
                    }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isEmailSendSuccessfully = false,
                            errorText = error.toUiText()
                        )
                    }
                }
        }
    }
}
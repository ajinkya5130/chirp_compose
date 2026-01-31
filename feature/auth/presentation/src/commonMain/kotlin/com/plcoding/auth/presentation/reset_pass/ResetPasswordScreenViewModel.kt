/**
Created by ajinkya on 26/10/25
 */
package com.plcoding.auth.presentation.reset_pass

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.error_reset_pass
import chirp.feature.auth.presentation.generated.resources.error_same_pass
import com.plcoding.core.domain.auth.IAuthService
import com.plcoding.core.domain.utils.DataError
import com.plcoding.core.domain.utils.UrlConstants
import com.plcoding.core.domain.utils.onFailure
import com.plcoding.core.domain.utils.onSuccess
import com.plcoding.core.domain.validations.PasswordValidator
import com.plcoding.core.presentation.utils.UiText
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

class ResetPasswordScreenViewModel(
    private val authService: IAuthService,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val token = savedStateHandle.get<String>(UrlConstants.KEY_TOKEN)
        ?: throw IllegalStateException("token is mismatch")


    private val isPasswordValidFlow = snapshotFlow {
        state.value.passwordField.text.toString()
    }.map {
        PasswordValidator.validate(it).isValidPassword
    }.distinctUntilChanged()

    private val _state = MutableStateFlow(ResetPasswordScreenState())
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
            initialValue = ResetPasswordScreenState()
        )

    fun observeValidation() {
        isPasswordValidFlow.onEach { isValidPass ->
            _state.update {
                it.copy(
                    canSubmit = isValidPass,
                    errorText = null,
                    isPasswordResetSuccessfully = false
                )
            }

        }.launchIn(viewModelScope)
    }

    fun onAction(action: ResetPasswordScreenAction) {
        when (action) {
            is ResetPasswordScreenAction.OnSubmitClick -> {
                resetPass()
            }

            is ResetPasswordScreenAction.OnPasswordToggle -> {
                _state.update {
                    it.copy(
                        passwordToggle = !it.passwordToggle
                    )
                }
            }
        }
    }

    fun resetPass() {
        if (state.value.isLoading || state.value.canSubmit.not()) {
            return
        }

        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    isPasswordResetSuccessfully = false
                )
            }

            val pass = state.value.passwordField.text.toString()
            authService
                .resetPassword(
                    token = token,
                    password = pass
                )
                .onSuccess {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isPasswordResetSuccessfully = true,
                            errorText = null
                        )
                    }

                }
                .onFailure { error ->
                    val errorMsg = when (error) {
                        DataError.Remote.UNAUTHORIZED -> {
                            UiText.Resource(Res.string.error_reset_pass)
                        }

                        DataError.Remote.CONFLICT -> {
                            UiText.Resource(Res.string.error_same_pass)
                        }

                        else -> error.toUiText()
                    }
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isPasswordResetSuccessfully = false,
                            errorText = errorMsg
                        )
                    }
                }
        }
    }

}
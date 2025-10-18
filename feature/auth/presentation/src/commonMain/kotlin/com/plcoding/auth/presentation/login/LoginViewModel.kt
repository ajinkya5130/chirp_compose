/**
Created by ajinkya on 18/10/25
 */
package com.plcoding.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.email_error
import chirp.feature.auth.presentation.generated.resources.error_invalid_credentials
import chirp.feature.auth.presentation.generated.resources.password_error
import com.plcoding.auth.domain.utils.EmailValidator
import com.plcoding.core.domain.validations.PasswordValidator
import com.plcoding.core.presentation.utils.UiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(LoginScreenState())
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
            initialValue = LoginScreenState()
        )

    fun onAction(action: LoginScreenAction) {
        when (action) {
            LoginScreenAction.OnLoginClick -> {
                validateFormInput()

            }

            LoginScreenAction.OnTogglePasswordVisibilityClick -> {
                _state.update {
                    it.copy(
                        isPasswordVisible = !it.isPasswordVisible
                    )
                }
            }

            else -> Unit
        }
    }

    private fun validateFormInput() {
        val email = state.value.emailTextFieldState.text.toString()
        val password = state.value.passwordTextFieldState.text.toString()
        if (email.isEmpty() || password.isEmpty()) {
            _state.update {
                it.copy(
                    loginError = UiText.Resource(id = Res.string.error_invalid_credentials)
                )
            }
            return
        }
        _state.update {
            it.copy(
                loginError = null
            )
        }
        if (EmailValidator.validate(email).not()) {
            _state.update {
                it.copy(
                    loginError = UiText.Resource(id = Res.string.email_error)
                )
            }
            return
        }
        _state.update {
            it.copy(
                loginError = null
            )
        }
        if (PasswordValidator.validate(password).isValidPassword.not()) {
            _state.update {
                it.copy(
                    loginError = UiText.Resource(id = Res.string.password_error)
                )
            }
            return
        }
        _state.update {
            it.copy(
                loginError = null,
                canLogin = true,
            )
        }

    }

}
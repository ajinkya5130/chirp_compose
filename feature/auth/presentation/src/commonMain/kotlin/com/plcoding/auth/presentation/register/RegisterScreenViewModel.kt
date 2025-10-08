/**
Created by ajinkya on 01/10/25
 */
package com.plcoding.auth.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.email_error
import chirp.feature.auth.presentation.generated.resources.error_account_exist
import chirp.feature.auth.presentation.generated.resources.error_invalid_username
import chirp.feature.auth.presentation.generated.resources.password_error
import com.plcoding.auth.domain.utils.EmailValidator
import com.plcoding.core.domain.auth.IAuthService
import com.plcoding.core.domain.utils.DataError
import com.plcoding.core.domain.utils.onFailure
import com.plcoding.core.domain.utils.onSuccess
import com.plcoding.core.domain.validations.PasswordValidator
import com.plcoding.core.presentation.utils.UiText
import com.plcoding.core.presentation.utils.toUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterScreenViewModel(
    private val authService: IAuthService,
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val eventChannel = Channel<RegisterEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(RegisterScreenState())
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
            initialValue = RegisterScreenState()
        )

    fun onAction(action: RegisterScreenAction) {
        when (action) {
            RegisterScreenAction.OnLoginClick -> Unit
            RegisterScreenAction.OnRegisterClick -> registerNewUser()
            RegisterScreenAction.OnTogglePasswordVisibilityClick -> {
                _state.update {
                    it.copy(
                        isPasswordVisible = !it.isPasswordVisible
                    )
                }
            }

            else -> Unit
        }
    }

    private fun registerNewUser() {
        if (!validateFormInput()) return

        viewModelScope.launch {
            _state.update {
                it.copy(
                    isRegistering = true,
                )
            }
            authService.register(
                email = state.value.emailTextState.text.toString(),
                password = state.value.passwordTextState.text.toString(),
                username = state.value.userNameTextState.text.toString(),
            ).onSuccess {
                _state.update {
                    it.copy(
                        isRegistering = false,
                    )
                }

            }.onFailure { it ->
                val registrationError = when (it) {
                    DataError.Remote.CONFLICT -> UiText.Resource(id = Res.string.error_account_exist)
                    else -> it.toUiText()
                }
                _state.update {
                    it.copy(
                        isRegistering = false,
                        registraionError = registrationError
                    )
                }

            }

        }

    }

    private fun validateFormInput(): Boolean {
        val currentState = state.value

        val email = currentState.emailTextState.text.toString()
        val userName = currentState.userNameTextState.text.toString()
        val password = currentState.passwordTextState.text.toString()

        val isValidEmail = EmailValidator.validate(email)
        val isValidUserName = userName.length in 3..20
        val isValidPassword = PasswordValidator.validate(password)

        if (isValidUserName.not()) {
            _state.update {
                it.copy(
                    isUserNameValid = false,
                    userNameError = UiText.Resource(id = Res.string.error_invalid_username)
                )
            }
            return false
        } else {
            _state.update {
                it.copy(
                    isUserNameValid = true,
                    userNameError = null
                )
            }
        }

        if (isValidEmail.not()) {
            _state.update {
                it.copy(
                    isEmailValid = false,
                    emailError = UiText.Resource(id = Res.string.email_error)
                )
            }
            return false
        } else {
            _state.update {
                it.copy(
                    isEmailValid = true,
                    emailError = null
                )
            }
        }

        if (isValidPassword.isValidPassword.not()) {
            _state.update {
                it.copy(
                    isPasswordValid = false,
                    passwordError = UiText.Resource(id = Res.string.password_error)
                )
            }
            return false
        } else {
            _state.update {
                it.copy(
                    isPasswordValid = true,
                    passwordError = null
                )
            }
        }

        return true
    }

}
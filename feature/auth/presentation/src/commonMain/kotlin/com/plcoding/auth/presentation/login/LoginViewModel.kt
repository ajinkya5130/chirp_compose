/**
Created by ajinkya on 18/10/25
 */
package com.plcoding.auth.presentation.login

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.error_email_not_verified
import chirp.feature.auth.presentation.generated.resources.error_invalid_credentials
import com.plcoding.auth.domain.utils.EmailValidator
import com.plcoding.core.domain.auth.IAuthService
import com.plcoding.core.domain.auth.ISessionDataStorage
import com.plcoding.core.domain.utils.DataError
import com.plcoding.core.domain.utils.onFailure
import com.plcoding.core.domain.utils.onSuccess
import com.plcoding.core.presentation.utils.UiText
import com.plcoding.core.presentation.utils.toUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for the login screen.
 *
 * Manages the login screen state, handles user authentication, and coordinates
 * with the authentication service and session storage.
 *
 * @property authService The authentication service for login operations
 * @property dataStore The session storage for persisting authentication data
 */
class LoginViewModel(
    private val authService: IAuthService,
    private val dataStore: ISessionDataStorage,
) : ViewModel() {

    private val channel = Channel<LoginEvent>()

    val events = channel.receiveAsFlow()

    private val _state = MutableStateFlow(LoginScreenState())

    private var hasLoadedInitialData = false
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                observeValidInputState()
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
            LoginScreenAction.OnLoginClick -> login()

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

    private val isEmailValidFlow = snapshotFlow {
        state.value.emailTextFieldState.text.toString()
    }.map {
        EmailValidator.validate(it)
    }.distinctUntilChanged()


    private val isPasswordNotEmptyFlow = snapshotFlow {
        state.value.passwordTextFieldState.text.toString()
    }.map {
        it.isNotBlank()
    }.distinctUntilChanged()

    private val isLoggingInFlow = state.map {
        it.isLoggingIn
    }.distinctUntilChanged()

    private fun observeValidInputState() {
        combine(
            isEmailValidFlow,
            isPasswordNotEmptyFlow,
            isLoggingInFlow
        ) { emailValid, passwordValid, isLoggingIn ->
            val allValid = emailValid && passwordValid
            _state.update {
                it.copy(
                    canLogin = allValid && !isLoggingIn
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun login() {

        if (state.value.canLogin.not()) return

        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoggingIn = true,
                )
            }
            authService.login(
                email = state.value.emailTextFieldState.text.toString(),
                password = state.value.passwordTextFieldState.text.toString(),
            ).onSuccess { authInfo ->
                //login success
                dataStore.saveAuthInfo(authInfo)
                _state.update {
                    it.copy(
                        isLoggingIn = false,
                    )
                }
                channel.send(LoginEvent.OnLoginSuccess)
            }.onFailure { onFailure ->

                val errorMessage = when (onFailure) {
                    DataError.Remote.UNAUTHORIZED -> UiText.Resource(id = Res.string.error_invalid_credentials)
                    DataError.Remote.FORBIDDEN -> {
                        channel.send(LoginEvent.OnEmailNotVerified)
                        UiText.Resource(id = Res.string.error_email_not_verified)
                    }

                    else -> onFailure.toUiText()
                }
                //login failed
                _state.update {
                    it.copy(
                        loginError = errorMessage,
                        isLoggingIn = false,
                    )
                }
            }
        }
    }


    fun resendVerificationEmail() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoggingIn = true,
                )
            }
            val email = state.value.emailTextFieldState.text.toString()
            authService.resendVerificationEmail(email)
                .onSuccess {
                    _state.update {
                        it.copy(
                            isLoggingIn = false,
                        )
                    }
                    channel.send(LoginEvent.OnEmailResentSuccess(email))
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            isLoggingIn = false,
                            loginError = error.toUiText()
                        )
                    }
                }
        }
    }

}
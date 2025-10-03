/**
Created by ajinkya on 01/10/25
 */
package com.plcoding.auth.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.email_error
import chirp.feature.auth.presentation.generated.resources.error_invalid_username
import chirp.feature.auth.presentation.generated.resources.password_error
import com.plcoding.auth.domain.utils.EmailValidator
import com.plcoding.core.domain.utils.PasswordValidator
import com.plcoding.core.presentation.utils.UiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class RegisterScreenViewModel : ViewModel() {

    private var hasLoadedInitialData = false

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
            RegisterScreenAction.OnLoginClick -> validateFormInput()
            else -> {

            }
        }
    }

    private fun validateFormInput() {
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
            return
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
            return
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
            return
        } else {
            _state.update {
                it.copy(
                    isPasswordValid = true,
                    passwordError = null
                )
            }
        }


    }

}
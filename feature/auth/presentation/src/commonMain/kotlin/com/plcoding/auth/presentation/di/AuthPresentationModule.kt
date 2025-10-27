package com.plcoding.auth.presentation.di

import com.plcoding.auth.presentation.email_verification.EmailVerificationViewModel
import com.plcoding.auth.presentation.forgot_pass.ForgotPassScreenViewModel
import com.plcoding.auth.presentation.login.LoginViewModel
import com.plcoding.auth.presentation.register.RegisterScreenViewModel
import com.plcoding.auth.presentation.register.register_success.RegisterSuccessViewModel
import com.plcoding.auth.presentation.reset_pass.ResetPasswordScreenViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authPresentationModule = module {
    viewModelOf(::RegisterScreenViewModel)
    viewModelOf(::RegisterSuccessViewModel)
    viewModelOf(::EmailVerificationViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::ForgotPassScreenViewModel)
    viewModelOf(::ResetPasswordScreenViewModel)
}
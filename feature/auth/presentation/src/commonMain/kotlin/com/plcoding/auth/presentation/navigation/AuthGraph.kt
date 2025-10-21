package com.plcoding.auth.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.plcoding.auth.presentation.email_verification.EmailVerificationScreenRoot
import com.plcoding.auth.presentation.login.LoginScreenRoot
import com.plcoding.auth.presentation.register.RegisterScreenRoot
import com.plcoding.auth.presentation.register.register_success.RegisterSuccessRoot
import com.plcoding.auth.presentation.utils.Utility.KEY_EMAIL_STRING
import com.plcoding.core.domain.utils.UrlConstants

fun NavGraphBuilder.authGraph(
    navController: NavController,
    onLoginSuccess: () -> Unit,
) {
    navigation<AuthGraphRoutes.Graph>(
        startDestination = AuthGraphRoutes.Login
    ) {

        composable<AuthGraphRoutes.Login> {
            LoginScreenRoot(
                onLoginSuccess = onLoginSuccess,
                onRegisterClick = {
                    navController.navigate(AuthGraphRoutes.Register) {
                        /*popUpTo(AuthGraphRoutes.Register) {
                            inclusive = true
                            saveState = true
                        }*/
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                onForgotPassword = {
                    navController.navigate(AuthGraphRoutes.ForgotPassword)
                },
                onEmailResentSuccess = {
                    navController.navigate(AuthGraphRoutes.RegisterSuccess(KEY_EMAIL_STRING))
                }
            )
        }

        composable<AuthGraphRoutes.ForgotPassword> {

        }

        composable<AuthGraphRoutes.Register> {
            RegisterScreenRoot(
                onRegisterSuccess = { email ->
                    navController.navigate(AuthGraphRoutes.RegisterSuccess(email))
                },
                onLoginClick = {
                    navController.navigate(AuthGraphRoutes.Login) {
                        popUpTo(AuthGraphRoutes.Register) {
                            inclusive = true
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }

        composable<AuthGraphRoutes.RegisterSuccess> {
            RegisterSuccessRoot(navigateToLoginScreen = {
                navController.navigate(AuthGraphRoutes.Login) {
                    popUpTo<AuthGraphRoutes.RegisterSuccess> {
                        inclusive = true
                    }
                }
            })
        }

        composable<AuthGraphRoutes.EmailVerification>(
            deepLinks = listOf(
                navDeepLink {
                    //https://chirp.pl-coding.com/api//auth/verify?token=token
                    val url =
                        "${UrlConstants.BASE_URL}${UrlConstants.API_ENDPOINT_VERIFY_EMAIL}?${UrlConstants.KEY_TOKEN}={${UrlConstants.KEY_TOKEN}}"
                    this.uriPattern = url
                },
                navDeepLink {
                    val url =
                        "${UrlConstants.BASE_URL_CHIRP}${UrlConstants.API_ENDPOINT_VERIFY_EMAIL}?${UrlConstants.KEY_TOKEN}={${UrlConstants.KEY_TOKEN}}"
                    this.uriPattern = url
                },
            )
        ) {
            EmailVerificationScreenRoot(
                onLoginClick = {
                    navController.navigate(AuthGraphRoutes.Login) {
                        popUpTo<AuthGraphRoutes.EmailVerification> {
                            inclusive = true
                        }
                    }
                },
                onCloseClick = {
                    navController.navigate(AuthGraphRoutes.Login) {
                        popUpTo<AuthGraphRoutes.EmailVerification> {
                            inclusive = true
                        }
                    }
                }
            )
        }

    }

}
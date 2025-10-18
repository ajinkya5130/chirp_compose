package com.plcoding.auth.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.plcoding.auth.presentation.email_verification.EmailVerificationScreenRoot
import com.plcoding.auth.presentation.register.RegisterScreenRoot
import com.plcoding.auth.presentation.register.register_success.RegisterSuccessRoot
import com.plcoding.core.domain.utils.UrlConstants

fun NavGraphBuilder.authGraph(
    navController: NavController,
    onLoginSuccess: () -> Unit,
) {
    navigation<AuthGraphRoutes.Graph>(
        startDestination = AuthGraphRoutes.Register
    ) {
        composable<AuthGraphRoutes.Register> {
            RegisterScreenRoot(
                onRegisterSuccess = { email ->
                    navController.navigate(AuthGraphRoutes.RegisterSuccess(email))
                }
            )
        }

        composable<AuthGraphRoutes.RegisterSuccess> {
            RegisterSuccessRoot()
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
            EmailVerificationScreenRoot()
        }

    }

}
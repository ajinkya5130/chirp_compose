package com.plcoding.core.data.networking

import com.plcoding.core.data.BuildKonfig
import com.plcoding.core.data.dto.AuthInfoSerializable
import com.plcoding.core.data.dto.requests.RefreshTokenRequest
import com.plcoding.core.data.dto.toAuthInfo
import com.plcoding.core.domain.auth.ISessionDataStorage
import com.plcoding.core.domain.logging.CustomLogger
import com.plcoding.core.domain.utils.UrlConstants.API_ENDPOINT_AUTH_REFRESH_TOKEN
import com.plcoding.core.domain.utils.UrlConstants.KEY_AUTH_WITH_SLASH
import com.plcoding.core.domain.utils.UrlConstants.TIMEOUT_VALUE
import com.plcoding.core.domain.utils.onFailure
import com.plcoding.core.domain.utils.onSuccess
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.request.header
import io.ktor.client.statement.request
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.json.Json

/**
 * Factory class for creating configured Ktor HTTP clients.
 *
 * This factory creates HTTP clients with pre-configured plugins including:
 * - Content negotiation with JSON serialization
 * - HTTP timeouts
 * - Request/response logging
 * - WebSocket support
 * - Default headers and API key authentication
 *
 * @property customLogger The logger instance for logging HTTP requests and responses
 */
class HttpClientFactory(
    val customLogger: CustomLogger,
    val iSessionDataStorage: ISessionDataStorage,
) {
    /**
     * Creates a configured HTTP client with the specified engine.
     *
     * The client is configured with all necessary plugins for the application's
     * networking needs, including JSON serialization, timeouts, logging, and WebSockets.
     *
     * @param engine The platform-specific HTTP client engine to use
     * @return A fully configured [HttpClient] instance
     */
    fun create(engine: HttpClientEngine): HttpClient {
        return HttpClient(engine) {
            //expectSuccess = true
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(HttpTimeout) {
                requestTimeoutMillis = TIMEOUT_VALUE
                connectTimeoutMillis = TIMEOUT_VALUE
                socketTimeoutMillis = TIMEOUT_VALUE
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        customLogger.debugLog(message)
                    }
                }
                level = LogLevel.ALL
            }
            install(WebSockets) {
                pingIntervalMillis = TIMEOUT_VALUE
            }

            defaultRequest {
                header("x-api-key", BuildKonfig.API_KEY)
                contentType(ContentType.Application.Json)
            }

            install(Auth) {
                bearer {
                    loadTokens {
                        iSessionDataStorage.getAuthInfo().firstOrNull()?.let {
                            BearerTokens(it.accessToken, it.refreshToken)
                        } ?: run {
                            null
                        }
                    }
                    refreshTokens {
                        if (response.request.url.encodedPath.contains(KEY_AUTH_WITH_SLASH)) {
                            return@refreshTokens null
                        }

                        val authInfo = iSessionDataStorage.getAuthInfo().firstOrNull()
                        if (authInfo?.refreshToken.isNullOrBlank()) {
                            iSessionDataStorage.clearAuthInfo()
                            return@refreshTokens null
                        }

                        var bearerTokens: BearerTokens? = null

                        client.post<RefreshTokenRequest, AuthInfoSerializable>(
                            route = API_ENDPOINT_AUTH_REFRESH_TOKEN,
                            body = RefreshTokenRequest(authInfo.refreshToken),
                            builder = {
                                markAsRefreshTokenRequest()
                            }
                        ).onSuccess { newAuthInfo ->
                            iSessionDataStorage.saveAuthInfo(newAuthInfo.toAuthInfo())
                            bearerTokens =
                                BearerTokens(newAuthInfo.accessToken, newAuthInfo.refreshToken)
                            return@refreshTokens bearerTokens


                        }.onFailure { error ->
                            iSessionDataStorage.clearAuthInfo()
                        }
                        //refreshTokenAPIImpl.refreshTokenAPI(authInfo.refreshToken)
                        bearerTokens

                    }
                }

            }

        }

    }
}
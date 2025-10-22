package com.plcoding.core.data.networking

import com.plcoding.core.domain.utils.DataError
import com.plcoding.core.domain.utils.Result
import com.plcoding.core.domain.utils.UrlConstants
import io.ktor.client.HttpClient
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse

/**
 * Platform-specific implementation of safe HTTP call execution.
 *
 * This expect function must be implemented by each platform to handle
 * platform-specific error handling and exception catching for HTTP requests.
 *
 * @param T The expected response type
 * @param execute The suspend function that executes the HTTP request
 * @param handleResponse The function that processes the HTTP response
 * @return Result containing the response data or a [DataError.Remote] on failure
 */
expect suspend fun <T> platformSpecificSafeCall(
    execute: suspend () -> HttpResponse,
    handleResponse: suspend (HttpResponse) -> Result<T, DataError.Remote>,
): Result<T, DataError.Remote>

/**
 * Executes an HTTP request safely with error handling.
 *
 * This function wraps HTTP requests in a safe call that catches exceptions
 * and converts them to appropriate [DataError.Remote] errors.
 *
 * @param T The expected response type
 * @param execute The suspend function that executes the HTTP request
 * @return Result containing the response data or a [DataError.Remote] on failure
 */
suspend inline fun <reified T> safeCall(noinline execute: suspend () -> HttpResponse): Result<T, DataError.Remote> {
    return platformSpecificSafeCall(
        execute = execute
    ) { rep: HttpResponse ->
        responseToResult(rep)
    }
}

/**
 * Performs a POST request with the specified parameters.
 *
 * This extension function sends a POST request to the specified route with
 * the provided body and optional query parameters. The request and response
 * are automatically serialized/deserialized.
 *
 * @param Request The type of the request body
 * @param Response The expected response type
 * @param route The API route (will be combined with base URL)
 * @param body The request body to send
 * @param queryParams Optional query parameters to include in the request
 * @param builder Optional builder for additional request configuration
 * @return Result containing the response data or a [DataError.Remote] on failure
 */
suspend inline fun <reified Request, reified Response : Any> HttpClient.post(
    route: String,
    body: Request,
    queryParams: Map<String, String> = emptyMap(),
    crossinline builder: HttpRequestBuilder.() -> Unit = {},
): Result<Response, DataError.Remote> {
    return safeCall {
        post {
            url(constructRoute(route))
            queryParams.forEach { (key, value) ->
                parameter(key, value)
            }
            setBody(body)
            builder()
        }
    }
}

/**
 * Performs a GET request with the specified parameters.
 *
 * This extension function sends a GET request to the specified route with
 * optional query parameters. The response is automatically deserialized.
 *
 * @param Response The expected response type
 * @param route The API route (will be combined with base URL)
 * @param queryParams Optional query parameters to include in the request
 * @param builder Optional builder for additional request configuration
 * @return Result containing the response data or a [DataError.Remote] on failure
 */
suspend inline fun <reified Response : Any> HttpClient.get(
    route: String,
    queryParams: Map<String, String> = emptyMap(),
    crossinline builder: HttpRequestBuilder.() -> Unit = {},
): Result<Response, DataError.Remote> {
    return safeCall {
        get {
            url(constructRoute(route))
            queryParams.forEach { (key, value) ->
                parameter(key, value)
            }
            //setBody(body)
            builder()
        }
    }
}

/**
 * Performs a DELETE request with the specified parameters.
 *
 * This extension function sends a DELETE request to the specified route with
 * optional query parameters and body. The request and response are automatically
 * serialized/deserialized.
 *
 * @param Request The type of the request body (if provided)
 * @param Response The expected response type
 * @param route The API route (will be combined with base URL)
 * @param queryParams Optional query parameters to include in the request
 * @param body Optional request body to send
 * @param builder Optional builder for additional request configuration
 * @return Result containing the response data or a [DataError.Remote] on failure
 */
suspend inline fun <reified Request, reified Response : Any> HttpClient.delete(
    route: String,
    queryParams: Map<String, String> = emptyMap(),
    body: Request? = null,
    crossinline builder: HttpRequestBuilder.() -> Unit = {},
): Result<Response, DataError.Remote> {
    return safeCall {
        delete {
            url(constructRoute(route))
            queryParams.forEach { (key, value) ->
                parameter(key, value)
            }
            setBody(body)
            builder()
        }
    }
}

/**
 * Performs a PUT request with the specified parameters.
 *
 * This extension function sends a PUT request to the specified route with
 * optional query parameters and body. The request and response are automatically
 * serialized/deserialized.
 *
 * @param Request The type of the request body (if provided)
 * @param Response The expected response type
 * @param route The API route (will be combined with base URL)
 * @param queryParams Optional query parameters to include in the request
 * @param body Optional request body to send
 * @param builder Optional builder for additional request configuration
 * @return Result containing the response data or a [DataError.Remote] on failure
 */
suspend inline fun <reified Request, reified Response : Any> HttpClient.put(
    route: String,
    queryParams: Map<String, String> = emptyMap(),
    body: Request? = null,
    crossinline builder: HttpRequestBuilder.() -> Unit = {},
): Result<Response, DataError.Remote> {
    return safeCall {
        put {
            url(constructRoute(route))
            queryParams.forEach { (key, value) ->
                parameter(key, value)
            }
            setBody(body)
            builder()
        }
    }
}

/**
 * Converts an HTTP response to a Result type.
 *
 * This function maps HTTP status codes to appropriate [DataError.Remote] errors
 * or deserializes successful responses to the expected type.
 *
 * @param T The expected response type
 * @param response The HTTP response to process
 * @return Result containing the deserialized response or a [DataError.Remote] on failure
 */
suspend inline fun <reified T> responseToResult(response: HttpResponse): Result<T, DataError.Remote> {
    return when (response.status.value) {
        in 200..299 -> try {
            Result.Success(response.body<T>())
        } catch (e: NoTransformationFoundException) {
            Result.Failure(DataError.Remote.SERIALIZATION_ERROR)
        }

        400 -> Result.Failure(DataError.Remote.BAD_REQUEST)
        401 -> Result.Failure(DataError.Remote.UNAUTHORIZED)
        403 -> Result.Failure(DataError.Remote.FORBIDDEN)
        404 -> Result.Failure(DataError.Remote.NOT_FOUND)
        408 -> Result.Failure(DataError.Remote.REQUEST_TIMEOUT)
        409 -> Result.Failure(DataError.Remote.CONFLICT)
        413 -> Result.Failure(DataError.Remote.PAYLOAD_TOO_LARGE)
        429 -> Result.Failure(DataError.Remote.TOO_MANY_REQUESTS)
        500 -> Result.Failure(DataError.Remote.SERVER_ERROR)
        503 -> Result.Failure(DataError.Remote.SERVICE_UNAVAILABLE)
        else -> Result.Failure(DataError.Remote.UNKNOWN)
    }
}

/**
 * Constructs a full URL from a route path.
 *
 * This function combines the base URL with the provided route to create a complete URL.
 * It handles routes that already contain the base URL, routes starting with "/",
 * and routes without a leading slash.
 *
 * @param route The API route path
 * @return The complete URL combining base URL and route
 */
fun constructRoute(route: String): String {
    //BASE_URL ---> "https://abc.com/api"
    return when {
        route.contains(UrlConstants.BASE_URL) -> route
        route.startsWith("/") -> UrlConstants.BASE_URL + route
        else -> UrlConstants.BASE_URL + "/" + route
    }
}
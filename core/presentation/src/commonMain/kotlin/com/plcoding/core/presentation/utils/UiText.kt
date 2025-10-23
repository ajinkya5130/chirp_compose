package com.plcoding.core.presentation.utils

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource

/**
 * Sealed interface representing text that can be displayed in the UI.
 *
 * This abstraction allows for both dynamic strings and string resources,
 * providing a type-safe way to handle UI text that may come from different sources.
 */
sealed interface UiText {
    /**
     * Represents a dynamic string value.
     *
     * @property value The actual string value to display
     */
    data class DynamicString(val value: String) : UiText

    /**
     * Represents a string resource with optional formatting arguments.
     *
     * @property id The string resource identifier
     * @property args Optional formatting arguments for the string resource
     */
    class Resource(
        val id: StringResource,
        val args: Array<Any> = arrayOf(),
    ) : UiText

    /**
     * Converts this UiText to a String within a Composable context.
     *
     * For [DynamicString], returns the value directly.
     * For [Resource], resolves the string resource with any provided arguments.
     *
     * @return The resolved string value
     */
    @Composable
    fun asString(): String {
        return when (this) {
            is DynamicString -> value
            is Resource -> stringResource(resource = id, *args)
        }
    }

    /**
     * Converts this UiText to a String asynchronously outside of a Composable context.
     *
     * For [DynamicString], returns the value directly.
     * For [Resource], resolves the string resource with any provided arguments.
     *
     * @return The resolved string value
     */
    suspend fun asStringAsync(): String {
        return when (this) {
            is DynamicString -> value
            is Resource -> getString(resource = id, *args)
        }
    }

}
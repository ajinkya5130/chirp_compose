package com.plcoding.core.data

/**
 * Returns the name of the current platform.
 *
 * This is an expect function that must be implemented by each platform (Android, iOS, etc.)
 * to provide platform-specific identification.
 *
 * @return A string identifying the current platform (e.g., "Android", "iOS")
 */
public expect fun platform(): String
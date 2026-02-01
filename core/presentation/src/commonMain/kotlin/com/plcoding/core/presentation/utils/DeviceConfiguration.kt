package com.plcoding.core.presentation.utils

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.window.core.layout.WindowSizeClass

/**
 * Remembers and returns the current device configuration based on window size.
 *
 * This composable function observes the window size and determines the appropriate
 * device configuration (mobile portrait/landscape, tablet portrait/landscape, or desktop).
 *
 * @return The current [DeviceConfiguration] based on window dimensions
 */
@Composable
fun rememberDeviceConfiguration(): DeviceConfiguration {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    return DeviceConfiguration.fromWindowSizeClass(windowSizeClass)
}

/**
 * Enum representing different device configurations based on screen size and orientation.
 *
 * This enum helps in creating adaptive UIs that respond to different device types and orientations.
 */
enum class DeviceConfiguration {
    /** Mobile device in portrait orientation */
    MOBILE_PORTRAIT,

    /** Mobile device in landscape orientation */
    MOBILE_LANDSCAPE,

    /** Tablet device in portrait orientation */
    TABLET_PORTRAIT,

    /** Tablet device in landscape orientation */
    TABLET_LANDSCAPE,

    /** Desktop or large screen device */
    DESKTOP;

    val isMobile: Boolean
        get() = this in listOf(MOBILE_PORTRAIT, MOBILE_LANDSCAPE)

    companion object {
        /**
         * Determines the device configuration from a [WindowSizeClass].
         *
         * This function analyzes the window dimensions and classifies the device
         * into one of the predefined configurations based on width and height breakpoints.
         *
         * @param windowSizeClass The window size class containing dimension information
         * @return The appropriate [DeviceConfiguration] for the given window size
         */
        fun fromWindowSizeClass(windowSizeClass: WindowSizeClass): DeviceConfiguration {
            return with(windowSizeClass) {
                when {
                    minWidthDp < WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND &&
                            minHeightDp >= WindowSizeClass.HEIGHT_DP_MEDIUM_LOWER_BOUND
                        -> MOBILE_PORTRAIT

                    minWidthDp >= WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND &&
                            minHeightDp < WindowSizeClass.HEIGHT_DP_MEDIUM_LOWER_BOUND
                        -> MOBILE_LANDSCAPE

                    minWidthDp in WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND..WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND &&
                            minHeightDp >= WindowSizeClass.HEIGHT_DP_MEDIUM_LOWER_BOUND
                        -> TABLET_PORTRAIT

                    minWidthDp >= WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND &&
                            minHeightDp in WindowSizeClass.HEIGHT_DP_MEDIUM_LOWER_BOUND..WindowSizeClass.HEIGHT_DP_EXPANDED_LOWER_BOUND
                        -> TABLET_LANDSCAPE

                    else -> DESKTOP
                }

            }
        }
    }
}

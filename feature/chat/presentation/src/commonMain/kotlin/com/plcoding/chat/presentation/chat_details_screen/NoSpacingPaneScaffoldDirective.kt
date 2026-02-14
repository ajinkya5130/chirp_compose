package com.plcoding.chat.presentation.chat_details_screen

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.PaneScaffoldDirective
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.plcoding.core.presentation.utils.DeviceConfiguration
import com.plcoding.core.presentation.utils.rememberDeviceConfiguration

/**
 * Creates a [PaneScaffoldDirective] with no horizontal spacing between panes.
 *
 * This composable function generates a custom pane scaffold directive that adapts to different
 * device configurations and window postures. It removes horizontal spacing between panes while
 * maintaining vertical spacing for tabletop mode.
 *
 * The directive configures:
 * - **Horizontal partitions**: 1 for mobile and tablet portrait, 2 for tablet landscape and desktop
 * - **Horizontal spacing**: Always 0dp (no spacing between horizontal panes)
 * - **Vertical partitions**: 2 for tabletop mode, 1 otherwise
 * - **Vertical spacing**: 24dp for tabletop mode, 0dp otherwise
 * - **Default pane width**: 360dp
 *
 * @return A [PaneScaffoldDirective] configured based on the current device configuration
 * and window posture, with no horizontal spacing between panes.
 *
 * @see PaneScaffoldDirective
 * @see DeviceConfiguration
 */
@Composable
fun createNoSpacingPaneScaffoldDirective(): PaneScaffoldDirective {
    val configuration = rememberDeviceConfiguration()
    val windowAdaptiveInfo = currentWindowAdaptiveInfo()

    val maxHorizontalPartitions = when (configuration) {
        DeviceConfiguration.MOBILE_PORTRAIT,
        DeviceConfiguration.MOBILE_LANDSCAPE,
        DeviceConfiguration.TABLET_PORTRAIT,
            -> 1

        DeviceConfiguration.TABLET_LANDSCAPE,
        DeviceConfiguration.DESKTOP,
            -> 2
    }

    val verticalPartitionSpacerSize: Dp
    val maxVerticalPartitions: Int

    if (windowAdaptiveInfo.windowPosture.isTabletop) {
        maxVerticalPartitions = 2
        verticalPartitionSpacerSize = 24.dp
    } else {
        maxVerticalPartitions = 1
        verticalPartitionSpacerSize = 0.dp
    }

    return PaneScaffoldDirective(
        maxHorizontalPartitions = maxHorizontalPartitions,
        horizontalPartitionSpacerSize = 0.dp,
        maxVerticalPartitions = maxVerticalPartitions,
        verticalPartitionSpacerSize = verticalPartitionSpacerSize,
        defaultPanePreferredWidth = 360.dp,
        excludedBounds = emptyList()
    )
}
package com.plcoding.core.designsystem.components.dialogs

import androidx.compose.runtime.Composable
import com.plcoding.core.presentation.utils.rememberDeviceConfiguration

@Composable
fun ChirpAdaptiveDialogSheetLayout(
    onDismiss: () -> Unit,
    content: @Composable () -> Unit,
) {
    val configuration = rememberDeviceConfiguration()
    if (configuration.isMobile) {
        ChirpBottomSheet(
            onDismiss = onDismiss,
            content = content
        )
    } else {
        ChirpDialogContent(
            onDismiss = onDismiss,
            content = content
        )
    }
}
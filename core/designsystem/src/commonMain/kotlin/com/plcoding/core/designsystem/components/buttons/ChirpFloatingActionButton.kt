package com.plcoding.core.designsystem.components.buttons

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.plcoding.core.designsystem.dimesions.LocalDim
import com.plcoding.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChirpFloatingActionButton(
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {},
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier,
        content = content,
        shape = RoundedCornerShape(LocalDim.current.dimen8dp)
    )
}

@Composable
@Preview
fun ChirpFloatingActionButtonPreview() {
    ChirpTheme {
        ChirpFloatingActionButton {
            Icon(
                Icons.Default.Add,
                contentDescription = null
            )
        }
    }
}

@Composable
@Preview
fun ChirpFloatingActionButtonDarkPreview() {
    ChirpTheme(darkTheme = true) {
        ChirpFloatingActionButton {
            Icon(
                Icons.Default.Add,
                contentDescription = null
            )
        }
    }
}
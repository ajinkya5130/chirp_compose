package com.plcoding.core.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.plcoding.core.designsystem.dimesions.LocalDim
import com.plcoding.core.designsystem.theme.ChirpTheme
import com.plcoding.core.designsystem.theme.extended
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChirpIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    val dim = LocalDim.current
    OutlinedIconButton(
        onClick = onClick,
        modifier = modifier.size(dim.dimen45dp),
        shape = RoundedCornerShape(dim.dimen8dp),
        colors = IconButtonDefaults.outlinedIconButtonColors(
            contentColor = MaterialTheme.colorScheme.extended.textSecondary,
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            width = dim.dimen1dp,
            color = MaterialTheme.colorScheme.outline
        )
    ) {
        content()
    }
}

@Composable
@Preview()
fun ChirpIconButtonPreview() {
    ChirpTheme {
        ChirpIconButton(Modifier, onClick = {}) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null
            )
        }
    }
}

@Composable
@Preview()
fun ChirpIconButtonDarkPreview() {
    ChirpTheme(darkTheme = true) {
        ChirpIconButton(Modifier, onClick = {}) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null
            )
        }
    }

}
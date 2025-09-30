package com.plcoding.core.designsystem.components.textfields

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.plcoding.core.designsystem.dimesions.LocalDim
import com.plcoding.core.designsystem.theme.extended

@Composable
fun ChirpTextFieldLayout(
    modifier: Modifier = Modifier,
    title: String? = null,
    supportingText: String? = null,
    isError: Boolean = false,
    onFocusChanged: (Boolean) -> Unit = {},
    enabled: Boolean = true,
    layout: @Composable (MutableInteractionSource, Modifier) -> Unit,
) {

    val interactionSource = remember {
        MutableInteractionSource()
    }
    val isFocused by interactionSource.collectIsFocusedAsState()

    LaunchedEffect(isFocused) {
        onFocusChanged(isFocused)
    }

    val dimen = LocalDim.current

    val customModifier = Modifier.fillMaxWidth().background(
        color = when {
            isFocused -> MaterialTheme.colorScheme.primary.copy(
                alpha = 0.05f
            )

            enabled -> MaterialTheme.colorScheme.surface
            else -> MaterialTheme.colorScheme.extended.secondaryFill
        },
        shape = RoundedCornerShape(dimen.dimen8dp)
    ).border(
        width = dimen.dimen1dp,
        color = when {
            isFocused -> MaterialTheme.colorScheme.primary
            isError -> MaterialTheme.colorScheme.error
            else -> MaterialTheme.colorScheme.outline
        },
        shape = RoundedCornerShape(dimen.dimen8dp)
    ).padding(dimen.dimen12dp)
    Column(modifier = modifier) {
        if (title != null) {
            Text(
                title,
                color = MaterialTheme.colorScheme.extended.textSecondary,
                style = MaterialTheme.typography.labelSmall
            )

            Spacer(Modifier.height(dimen.dimen8dp))
        }
        layout(interactionSource, customModifier)

        if (supportingText != null) {
            Spacer(modifier = Modifier.height(dimen.dimen4dp))
            Text(
                text = supportingText,
                color = if (isError) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.extended.textTertiary
                },
                style = MaterialTheme.typography.bodySmall
            )
        }


    }


}
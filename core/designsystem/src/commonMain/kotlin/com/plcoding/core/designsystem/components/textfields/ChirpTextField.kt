package com.plcoding.core.designsystem.components.textfields

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.plcoding.core.designsystem.dimesions.LocalDim
import com.plcoding.core.designsystem.theme.ChirpTheme
import com.plcoding.core.designsystem.theme.extended
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChirpTextField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    placeHolder: String? = null,
    title: String? = null,
    supportingText: String? = null,
    isError: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    singleLine: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    onFocusChanged: (Boolean) -> Unit = {},
    enabled: Boolean = true,
) {

    val interactionSource = remember {
        MutableInteractionSource()
    }
    val isFocused by interactionSource.collectIsFocusedAsState()

    LaunchedEffect(isFocused) {
        onFocusChanged(isFocused)
    }

    val dimen = LocalDim.current
    Column(modifier = modifier) {
        if (title != null) {
            Text(
                title,
                color = MaterialTheme.colorScheme.extended.textSecondary,
                style = MaterialTheme.typography.labelSmall
            )

            Spacer(Modifier.height(dimen.dimen8dp))
        }
        BasicTextField(
            state = state,
            lineLimits = if (singleLine) {
                TextFieldLineLimits.SingleLine
            } else TextFieldLineLimits.Default,
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = if (enabled) {
                    MaterialTheme.colorScheme.onSurface
                } else {
                    MaterialTheme.colorScheme.extended.textPlaceholder
                }
            ),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
            interactionSource = interactionSource,
            modifier = Modifier.fillMaxWidth().background(
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
            ).padding(dimen.dimen12dp),
            decorator = { innerBox ->
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (placeHolder != null && state.text.isEmpty()) {
                        Text(
                            placeHolder,
                            color = MaterialTheme.colorScheme.extended.textPlaceholder,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    innerBox()
                }
            }
        )

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

@Composable
@Preview(showBackground = true)
fun ChirpTextFieldEmptyPreview() {
    ChirpTheme {
        ChirpTextField(
            state = rememberTextFieldState(),
            title = "Email",
            supportingText = "Please enter your mail",
            modifier = Modifier.width(300.dp),
            placeHolder = "test@test.com",

            )
    }
}

@Composable
@Preview(showBackground = true)
fun ChirpTextFieldFilledPreview() {
    ChirpTheme {
        ChirpTextField(
            state = rememberTextFieldState("test@test.com"),
            title = "Email",
            supportingText = "Please enter your mail",
            modifier = Modifier.width(300.dp),
            placeHolder = "test@test.com",

            )
    }
}

@Composable
@Preview(showBackground = true)
fun ChirpTextFieldDisabledPreview() {
    ChirpTheme {
        ChirpTextField(
            state = rememberTextFieldState("test@test.com"),
            title = "Email",
            supportingText = "Please enter your mail",
            modifier = Modifier.width(300.dp),
            placeHolder = "test@test.com",
            enabled = false

        )
    }
}

@Composable
@Preview(showBackground = true)
fun ChirpTextFieldErrorPreview() {
    ChirpTheme {
        ChirpTextField(
            state = rememberTextFieldState("test@test.com"),
            title = "Email",
            modifier = Modifier.width(300.dp),
            placeHolder = "test@test.com",
            isError = true,
            supportingText = "This is not valid email"

        )
    }
}
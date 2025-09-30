package com.plcoding.core.designsystem.components.textfields

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import chirp.core.designsystem.generated.resources.Res
import chirp.core.designsystem.generated.resources.eye_icon
import chirp.core.designsystem.generated.resources.eye_off_icon
import chirp.core.designsystem.generated.resources.hide_password
import chirp.core.designsystem.generated.resources.show_password
import com.plcoding.core.designsystem.dimesions.LocalDim
import com.plcoding.core.designsystem.theme.ChirpTheme
import com.plcoding.core.designsystem.theme.extended
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChirpPasswordField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    placeHolder: String? = null,
    title: String? = null,
    supportingText: String? = null,
    isError: Boolean = false,
    enabled: Boolean = true,
    isPasswordVisible: Boolean = true,
    onTogglePasswordVisibility: () -> Unit = {},
    onFocusChanged: (Boolean) -> Unit = {},
) {

    ChirpTextFieldLayout(
        modifier = modifier,
        title = title,
        supportingText = supportingText,
        isError = isError,
        onFocusChanged = onFocusChanged,
        enabled = enabled,
    ) { interactionSource, customModifier ->
        BasicSecureTextField(
            state = state,
            modifier = customModifier,
            enabled = enabled,
            textObfuscationMode = if (isPasswordVisible) {
                TextObfuscationMode.Visible
            } else TextObfuscationMode.Hidden,
            textObfuscationCharacter = '*',
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = if (enabled) {
                    MaterialTheme.colorScheme.onSurface
                } else {
                    MaterialTheme.colorScheme.extended.textPlaceholder
                }
            ),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
            interactionSource = interactionSource,
            decorator = { innerBox ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.weight(1f),
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
                    Icon(
                        imageVector = if (isPasswordVisible) vectorResource(Res.drawable.eye_off_icon) else vectorResource(
                            Res.drawable.eye_icon
                        ),
                        contentDescription = if (isPasswordVisible) stringResource(Res.string.hide_password) else stringResource(
                            Res.string.show_password
                        ),
                        tint = MaterialTheme.colorScheme.extended.textDisabled,
                        modifier = Modifier.size(LocalDim.current.dimen24dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = ripple(
                                    bounded = false,
                                    radius = LocalDim.current.dimen24dp
                                ),
                                onClick = onTogglePasswordVisibility
                            )
                    )
                }
            }
        )

    }
}

@Composable
@Preview(showBackground = true)
fun ChirpPasswordFieldEmptyPreview() {
    ChirpTheme {
        ChirpPasswordField(
            state = rememberTextFieldState(""),
            title = "password",
            isPasswordVisible = false,
            isError = false,
            supportingText = "Please enter valid password",
            modifier = Modifier.width(300.dp),
            placeHolder = "*****",
            onTogglePasswordVisibility = {

            }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun ChirpPasswordFieldFilledPreview() {
    ChirpTheme {
        ChirpPasswordField(
            state = rememberTextFieldState("password123"),
            title = "password",
            isPasswordVisible = true,
            isError = true,
            supportingText = "Please enter valid password",
            modifier = Modifier.width(300.dp),
            placeHolder = "test@test.com",
            onTogglePasswordVisibility = {

            }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun ChirpPasswordFieldErrorPreview() {
    ChirpTheme {
        ChirpPasswordField(
            state = rememberTextFieldState("password123"),
            title = "password",
            isPasswordVisible = true,
            isError = true,
            supportingText = "Please enter valid password",
            modifier = Modifier.width(300.dp),
            placeHolder = "test@test.com",
            onTogglePasswordVisibility = {

            }
        )
    }
}
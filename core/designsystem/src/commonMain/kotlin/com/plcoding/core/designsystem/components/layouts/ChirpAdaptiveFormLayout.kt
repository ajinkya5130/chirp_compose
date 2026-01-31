package com.plcoding.core.designsystem.components.layouts

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.plcoding.core.designsystem.components.branding.ChirpBrandingLogo
import com.plcoding.core.designsystem.dimesions.LocalDim
import com.plcoding.core.designsystem.theme.ChirpTheme
import com.plcoding.core.designsystem.theme.extended
import com.plcoding.core.presentation.utils.DeviceConfiguration
import com.plcoding.core.presentation.utils.clearFocusOnTap
import com.plcoding.core.presentation.utils.rememberDeviceConfiguration
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChirpAdaptiveFormLayout(
    modifier: Modifier = Modifier,
    header: String,
    errorText: String? = null,
    logo: @Composable () -> Unit = {},
    formContent: @Composable ColumnScope.() -> Unit = {},
) {
    val dimen = LocalDim.current
    val deviceConfiguration = rememberDeviceConfiguration()
    val headerColor = if (deviceConfiguration == DeviceConfiguration.MOBILE_LANDSCAPE) {
        MaterialTheme.colorScheme.onBackground
    } else MaterialTheme.colorScheme.extended.textPrimary

    when (deviceConfiguration) {
        DeviceConfiguration.MOBILE_PORTRAIT -> {
            ChirpSurface(
                modifier = modifier
                    .clearFocusOnTap()
                    .consumeWindowInsets(WindowInsets.navigationBars)
                    .consumeWindowInsets(WindowInsets.displayCutout),
                header = {
                    ChirpSpacerHeight(dimen.dimen32dp)
                    logo()
                    ChirpSpacerHeight(dimen.dimen32dp)
                }) {
                ChirpSpacerHeight(value = dimen.dimen24dp)
                AuthHeaderSection(
                    headerText = header,
                    headerColor = headerColor,
                    errorText = errorText
                )
                ChirpSpacerHeight(value = dimen.dimen24dp)
                formContent()
            }
        }

        DeviceConfiguration.MOBILE_LANDSCAPE -> {
            Row(
                horizontalArrangement = Arrangement.spacedBy(dimen.dimen16dp),
                modifier = modifier
                    .fillMaxSize()
                    .consumeWindowInsets(WindowInsets.displayCutout)
                    .consumeWindowInsets(WindowInsets.navigationBars)
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(dimen.dimen24dp)
                ) {
                    ChirpSpacerHeight(value = dimen.dimen16dp)
                    logo()
                    AuthHeaderSection(
                        headerText = header,
                        headerColor = headerColor,
                        errorText = errorText,
                        textAlign = TextAlign.Start,
                    )
                }
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    ChirpSurface() {
                        formContent()
                    }
                }

            }

        }

        DeviceConfiguration.TABLET_PORTRAIT,
        DeviceConfiguration.TABLET_LANDSCAPE,
        DeviceConfiguration.DESKTOP,
            -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(dimen.dimen32dp),
                modifier = modifier
                    .fillMaxSize()
                    .padding(dimen.dimen32dp)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                logo()
                Column(
                    modifier = Modifier
                        .widthIn(max = dimen.dimen480dp)
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(dimen.dimen32dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(horizontal = dimen.dimen24dp, vertical = dimen.dimen32dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AuthHeaderSection(
                        headerText = header,
                        headerColor = headerColor,
                        errorText = errorText,
                        textAlign = TextAlign.Start,
                    )
                    ChirpSpacerHeight(value = dimen.dimen24dp)
                    formContent()
                }

            }
        }
    }
}

@Composable
fun ColumnScope.AuthHeaderSection(
    modifier: Modifier = Modifier,
    headerText: String,
    headerColor: Color,
    errorText: String? = null,
    textAlign: TextAlign = TextAlign.Center,
) {
    Text(
        text = headerText,
        style = MaterialTheme.typography.titleLarge,
        color = headerColor,
        textAlign = textAlign,
        modifier = Modifier.fillMaxWidth()
    )
    AnimatedVisibility(errorText != null) {
        if (errorText != null) {

            Text(
                text = errorText,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.fillMaxWidth(),
                textAlign = textAlign
            )
        }

    }

}

@Composable
@Preview
fun ChirpAdaptiveFormLayoutLightPreview() {
    ChirpTheme {
        ChirpAdaptiveFormLayout(
            header = "Welcome to Chirp!",
            errorText = "Login Failed!",
            formContent = {
                Text(
                    "Sample text",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text("Sample text")
            },
            logo = {
                ChirpBrandingLogo()
            }
        )
    }
}

@Composable
@Preview
fun ChirpAdaptiveFormLayoutDarkPreview() {
    ChirpTheme(darkTheme = true) {
        ChirpAdaptiveFormLayout(
            header = "Welcome to Chirp!",
            errorText = "Login Failed!",
            formContent = {
                Text(
                    "Sample text",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text("Sample text")
            },
            logo = {
                ChirpBrandingLogo()
            }
        )
    }
}
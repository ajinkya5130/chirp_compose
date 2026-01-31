package com.plcoding.core.designsystem.components.layouts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.plcoding.core.designsystem.components.branding.ChirpBrandingLogo
import com.plcoding.core.designsystem.dimesions.LocalDim
import com.plcoding.core.designsystem.theme.ChirpTheme
import com.plcoding.core.presentation.utils.DeviceConfiguration
import com.plcoding.core.presentation.utils.rememberDeviceConfiguration
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChirpAdaptiveResultLayout(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit = {},
) {
    val dimen = LocalDim.current
    val deviceConfiguration = rememberDeviceConfiguration()
    Scaffold(modifier = modifier) { innerPadding ->
        if (deviceConfiguration == DeviceConfiguration.MOBILE_PORTRAIT) {
            ChirpSurface(
                modifier = Modifier.padding(innerPadding),
                header = {
                    ChirpSpacerHeight(dimen.dimen32dp)
                    ChirpBrandingLogo()
                    ChirpSpacerHeight(dimen.dimen32dp)
                },
                content = content
            )
        } else {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize()
                    .padding(top = dimen.dimen32dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(dimen.dimen32dp)
            ) {
                if (deviceConfiguration != DeviceConfiguration.MOBILE_LANDSCAPE) {
                    ChirpBrandingLogo()
                }
                Column(
                    modifier = Modifier
                        .widthIn(max = dimen.dimen480dp)
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(dimen.dimen32dp)
                        )
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(horizontal = dimen.dimen24dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(dimen.dimen24dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    content()
                }
            }

        }


    }
}

@Composable
@Preview
fun ChirpAdaptiveResultLayoutPreview() {
    ChirpTheme {
        ChirpAdaptiveResultLayout(
            modifier = Modifier.fillMaxSize(),
            content = {
                Text(
                    "Sample text",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        )
    }
}
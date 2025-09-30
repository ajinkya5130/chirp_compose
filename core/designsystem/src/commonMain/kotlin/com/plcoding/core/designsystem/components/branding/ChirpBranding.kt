package com.plcoding.core.designsystem.components.branding

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import chirp.core.designsystem.generated.resources.Res
import chirp.core.designsystem.generated.resources.logo_chirp
import com.plcoding.core.designsystem.dimesions.LocalDim
import org.jetbrains.compose.resources.vectorResource

@Composable
fun ChirpBrandingLogo(modifier: Modifier = Modifier) {
    Icon(
        vectorResource(Res.drawable.logo_chirp),
        contentDescription = null,
        tint = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(LocalDim.current.dimen32dp)
    )
}
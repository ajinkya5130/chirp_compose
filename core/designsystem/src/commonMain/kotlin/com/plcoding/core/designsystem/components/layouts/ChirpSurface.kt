package com.plcoding.core.designsystem.components.layouts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import chirp.core.designsystem.generated.resources.Res
import chirp.core.designsystem.generated.resources.logo_chirp
import com.plcoding.core.designsystem.dimesions.LocalDim
import com.plcoding.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChirpSurface(
    modifier: Modifier = Modifier,
    header: @Composable ColumnScope.() -> Unit = {},
    content: @Composable ColumnScope.() -> Unit = {},
) {
    val dimen = LocalDim.current
    Surface(modifier = modifier, color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            header()
            Surface(
                color = MaterialTheme.colorScheme.surface, modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(topStart = dimen.dimen20dp, topEnd = dimen.dimen20dp)
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    content()
                }
            }

        }

    }

}

@Composable
@Preview
fun ChirpSurfacePreview() {
    ChirpTheme {
        ChirpSurface(
            modifier = Modifier.fillMaxSize(),
            header = {
                Icon(
                    vectorResource(Res.drawable.logo_chirp),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(LocalDim.current.dimen32dp)
                )
            },
            content = {
                Text(
                    "Welcome to Live Chat Chirp",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier
                        .padding(vertical = LocalDim.current.dimen40dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        )
    }
}
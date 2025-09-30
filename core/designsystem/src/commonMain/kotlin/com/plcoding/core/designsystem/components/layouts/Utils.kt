package com.plcoding.core.designsystem.components.layouts

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun ChirpSpacerHeight(value: Dp) {
    Spacer(modifier = Modifier.height(value))
}

@Composable
fun ChirpSpacerWidth(value: Dp) {
    Spacer(modifier = Modifier.width(value))
}
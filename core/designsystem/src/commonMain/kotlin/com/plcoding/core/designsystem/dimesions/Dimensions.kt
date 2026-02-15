package com.plcoding.core.designsystem.dimesions

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val LocalDim = compositionLocalOf { Dimensions() }

val LocalFont = compositionLocalOf { FontDimensions() }

data class Dimensions(
    val dimenMinus48dp: Dp = (-48).dp,
    val dimenMinus25dp: Dp = (-25).dp,
    val default: Dp = 0.dp,
    val dimen1dp: Dp = 1.dp,
    val dimen2dp: Dp = 2.dp,
    val dimen3dp: Dp = 3.dp,
    val dimen4dp: Dp = 4.dp,
    val dimen5dp: Dp = 5.dp,
    val dimen6dp: Dp = 6.dp,
    val dimen8dp: Dp = 8.dp,
    val dimen10dp: Dp = 10.dp,
    val dimen12dp: Dp = 12.dp,
    val dimen16dp: Dp = 16.dp,
    val dimen15dp: Dp = 15.dp,
    val iconSize: Dp = 24.dp,
    val iconButtonSize: Dp = 48.dp,
    val horizontalPadding: Dp = 16.dp,
    val verticalPadding: Dp = 16.dp,
    val dimen18dp: Dp = 18.dp,
    val dimen20dp: Dp = 20.dp,
    val dimen24dp: Dp = 24.dp,
    val dimen25dp: Dp = 25.dp,
    val dimen28dp: Dp = 28.dp,
    val dimen30dp: Dp = 30.dp,
    val dimen32dp: Dp = 32.dp,
    val dimen35dp: Dp = 35.dp,
    val dimen40dp: Dp = 40.dp,
    val dimen45dp: Dp = 45.dp,
    val dimen48dp: Dp = 48.dp,
    val dimen50dp: Dp = 50.dp,
    val dimen60dp: Dp = 60.dp,
    val dimen70dp: Dp = 70.dp,
    val dimen80dp: Dp = 80.dp,
    val dimen88dp: Dp = 80.dp,
    val dimen100dp: Dp = 100.dp,
    val dimen120dp: Dp = 120.dp,
    val dimen125dp: Dp = 125.dp,
    val dimen130dp: Dp = 130.dp,
    val dimen140dp: Dp = 140.dp,
    val dimen150dp: Dp = 150.dp,
    val dimen160dp: Dp = 160.dp,
    val dimen200dp: Dp = 200.dp,
    val dimen230dp: Dp = 230.dp,
    val dimen300dp: Dp = 300.dp,
    val dimen480dp: Dp = 480.dp,
)


data class FontDimensions(
    val sp0: TextUnit = 0.sp,
    val sp10: TextUnit = 10.sp,
    val sp11: TextUnit = 11.sp,
    val sp12: TextUnit = 12.sp,
    val sp14: TextUnit = 14.sp,
    val sp15: TextUnit = 15.sp,
    val sp16: TextUnit = 16.sp,
    val sp18: TextUnit = 18.sp,
    val sp20: TextUnit = 20.sp,
    val sp22: TextUnit = 22.sp,
    val sp24: TextUnit = 24.sp,
    val sp40: TextUnit = 40.sp,
    val sp48: TextUnit = 48.sp,
)

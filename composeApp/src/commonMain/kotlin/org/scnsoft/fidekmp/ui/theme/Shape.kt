package org.scnsoft.fidekmp.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(0.dp)
)
val gradientBrush = Brush.verticalGradient(colors = listOf(SplashEnd, SplashStart))
val disabledGradientBrush = Brush.verticalGradient(colors = listOf(SplashEnd, clientListNumBackColor))
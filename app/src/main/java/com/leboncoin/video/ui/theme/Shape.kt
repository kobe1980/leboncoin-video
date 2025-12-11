package com.leboncoin.video.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(16.dp)
)

// Custom shapes for specific use cases
object CustomShapes {
    val card = RoundedCornerShape(12.dp)
    val button = RoundedCornerShape(8.dp)
    val bottomSheet = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    val bottomBar = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
}
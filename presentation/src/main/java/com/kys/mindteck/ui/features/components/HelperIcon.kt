package com.kys.mindteck.ui.features.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun HelperIcon(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    iconSize: Dp = 24.dp,
    backgroundShape: Shape = CircleShape,
    iconTint: Color = Color.DarkGray,
    circleBackgroundTint: Color = Color.Gray,
    contentDescription: String = "Clickable icon",
) {
    Box(
        modifier = modifier
            .size(36.dp)
            .background(
                color = circleBackgroundTint,
                shape = backgroundShape
            )
    ) {
        Icon(
            imageVector = imageVector,
            modifier = Modifier
                .size(iconSize)
                .align(Alignment.Center),
            contentDescription = contentDescription,
            tint = iconTint.copy(alpha = 0.8f)
        )
    }
}
@Composable
fun HelperIcon(
    modifier: Modifier = Modifier,
    painter: Painter,
    iconSize: Dp = 24.dp,
    backgroundShape: Shape = CircleShape,
    iconTint: Color = Color.DarkGray,
    circleBackgroundTint: Color = Color.Gray,
    contentDescription: String = "Clickable icon",
) {
    Box(
        modifier = modifier
            .size(36.dp)
            .background(
                color = circleBackgroundTint,
                shape = backgroundShape
            )
    ) {
        Icon(
            painter = painter,
            modifier = Modifier
                .size(iconSize)
                .align(Alignment.Center),
            contentDescription = contentDescription,
            tint = iconTint
        )
    }
}
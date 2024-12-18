package com.kys.mindteck.ui.features.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun ClickableIcon(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    iconSize: Dp = 24.dp,
    backgroundShape: Shape = CircleShape,
    iconTint: Color = Color.DarkGray.copy(alpha = 0.8f),
    circleBackgroundTint: Color = Color.Gray,
    contentDescription: String = "Clickable icon",
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick
    ) {
        HelperIcon(
            modifier = modifier,
            imageVector = imageVector,
            iconSize = iconSize,
            backgroundShape = backgroundShape,
            iconTint = iconTint,
            circleBackgroundTint = circleBackgroundTint,
            contentDescription = contentDescription
        )
    }
}
@Composable
fun ClickableIcon(
    modifier: Modifier = Modifier,
    painter: Painter,
    iconSize: Dp = 24.dp,
    backgroundShape: Shape = CircleShape,
    iconTint: Color = Color.DarkGray.copy(alpha = 0.8f),
    circleBackgroundTint: Color = Color.Gray,
    contentDescription: String = "Clickable icon",
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick
    ) {
        HelperIcon(
            modifier = modifier,
            painter = painter,
            iconSize = iconSize,
            backgroundShape = backgroundShape,
            iconTint = iconTint,
            circleBackgroundTint = circleBackgroundTint,
            contentDescription = contentDescription
        )
    }
}
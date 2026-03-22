package com.bmnv.bmnvbutton

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

enum class ButtonVariant {
    Filled, Outlined, Tonal
}

enum class IconPosition {
    Leading, Trailing
}

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: ButtonVariant = ButtonVariant.Filled,
    icon: ImageVector? = null,
    iconPosition: IconPosition = IconPosition.Leading,
    backgroundColor: Color? = null,
    contentColor: Color? = null,
    cornerRadius: Dp = 8.dp,
    isLoading: Boolean = false,
    enabled: Boolean = true
) {
    val shape = RoundedCornerShape(cornerRadius)

    val colors = when (variant) {
        ButtonVariant.Filled -> ButtonDefaults.buttonColors(
            containerColor = backgroundColor ?: MaterialTheme.colorScheme.primary,
            contentColor = contentColor ?: MaterialTheme.colorScheme.onPrimary
        )

        ButtonVariant.Outlined -> ButtonDefaults.outlinedButtonColors(
            contentColor = contentColor ?: MaterialTheme.colorScheme.primary
        )

        ButtonVariant.Tonal -> ButtonDefaults.filledTonalButtonColors(
            containerColor = backgroundColor ?: MaterialTheme.colorScheme.secondaryContainer,
            contentColor = contentColor ?: MaterialTheme.colorScheme.onSecondaryContainer
        )
    }

    val border = if (variant == ButtonVariant.Outlined) {
        BorderStroke(1.dp, backgroundColor ?: MaterialTheme.colorScheme.outline)
    } else null

    Box(contentAlignment = Alignment.Center, modifier = modifier) {
        val buttonContent: @Composable RowScope.() -> Unit = {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = contentColor ?: colors.contentColor,
                    strokeWidth = 2.dp
                )
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    if (icon != null && iconPosition == IconPosition.Leading) {
                        Icon(icon, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                    }
                    Text(text)
                    if (icon != null && iconPosition == IconPosition.Trailing) {
                        Spacer(Modifier.width(8.dp))
                        Icon(icon, contentDescription = null, modifier = Modifier.size(18.dp))
                    }
                }
            }
        }

        when (variant) {
            ButtonVariant.Filled -> Button(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth(),
                enabled = enabled && !isLoading,
                shape = shape,
                colors = colors,
                content = buttonContent
            )

            ButtonVariant.Outlined -> OutlinedButton(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth(),
                enabled = enabled && !isLoading,
                shape = shape,
                colors = colors,
                border = border,
                content = buttonContent
            )

            ButtonVariant.Tonal -> FilledTonalButton(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth(),
                enabled = enabled && !isLoading,
                shape = shape,
                colors = colors,
                content = buttonContent
            )
        }
    }
}

@Composable
fun SliderButton(
    onComplete: () -> Unit,
    modifier: Modifier = Modifier,
    text: String = "Swipe to unlock",
    handleIcon: ImageVector? = null,
    handleColor: Color = MaterialTheme.colorScheme.primary,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    contentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    shape: RoundedCornerShape = RoundedCornerShape(50.dp),
    isLoading: Boolean = false
) {
    var containerWidth by remember { mutableIntStateOf(0) }
    var handleWidth by remember { mutableIntStateOf(0) }

    val maxOffset = remember(containerWidth, handleWidth) {
        (containerWidth - handleWidth).toFloat().coerceAtLeast(0f)
    }

    val offsetX = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(shape)
            .background(backgroundColor)
            .onSizeChanged { containerWidth = it.width },
        contentAlignment = Alignment.CenterStart
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .size(24.dp),
                color = handleColor,
                strokeWidth = 2.dp
            )
        } else {
            Text(
                text = text,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = contentColor,
                style = MaterialTheme.typography.labelLarge
            )

            Box(
                modifier = Modifier
                    .offset { IntOffset(offsetX.value.roundToInt(), 0) }
                    .padding(4.dp)
                    .size(48.dp)
                    .onSizeChanged { handleWidth = it.width }
                    .clip(CircleShape)
                    .background(handleColor)
                    .draggable(
                        orientation = Orientation.Horizontal,
                        state = rememberDraggableState { delta ->
                            scope.launch {
                                val newValue = (offsetX.value + delta).coerceIn(0f, maxOffset)
                                offsetX.snapTo(newValue)
                            }
                        },
                        onDragStopped = {
                            if (offsetX.value >= maxOffset * 0.9f) {
                                scope.launch {
                                    offsetX.animateTo(maxOffset)
                                    onComplete()
                                    offsetX.animateTo(0f)
                                }
                            } else {
                                scope.launch {
                                    offsetX.animateTo(0f)
                                }
                            }
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (handleIcon != null) {
                    Icon(
                        imageVector = handleIcon,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ComponentGalleryPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Component Gallery", fontWeight = FontWeight.Bold, fontSize = 20.sp)

            var filledIsLoading by remember { mutableStateOf(false) }

            CustomButton(
                text = "Filled Loading",
                onClick = {
                    filledIsLoading = true
                },
                isLoading = filledIsLoading,
                variant = ButtonVariant.Filled,
                modifier = Modifier.fillMaxWidth()
            )

            var outlined0IsLoading by remember { mutableStateOf(false) }

            CustomButton(
                text = "Outlined Loading",
                onClick = {
                    outlined0IsLoading = true
                },
                isLoading = outlined0IsLoading,
                variant = ButtonVariant.Outlined,
                modifier = Modifier.fillMaxWidth()
            )

            var tonalIsLoading by remember { mutableStateOf(false) }

            CustomButton(
                text = "Tonal Loading",
                onClick = {
                    tonalIsLoading = true
                },
                isLoading = tonalIsLoading,
                variant = ButtonVariant.Tonal,
                modifier = Modifier.fillMaxWidth()
            )

            var outlinedIsLoading by remember { mutableStateOf(false) }

            CustomButton(
                text = "Outlined with Icon",
                variant = ButtonVariant.Outlined,
                onClick = {
                    outlinedIsLoading = true
                },
                icon = Icons.Default.Add,
                modifier = Modifier.fillMaxWidth(),
                isLoading = outlinedIsLoading
            )


            var slider1IsLoading by remember { mutableStateOf(false) }

            SliderButton(
                onComplete = {
                    slider1IsLoading = true
                },
                text = "Swipe to Confirm",
                handleIcon = Icons.AutoMirrored.Filled.ArrowForward,
                modifier = Modifier.fillMaxWidth(),
                isLoading = slider1IsLoading
            )


            var sliderIsLoading by remember { mutableStateOf(false) }

            SliderButton(
                onComplete = {
                    sliderIsLoading = true
                },
                text = "Loading Slider",
                isLoading = sliderIsLoading,
                modifier = Modifier.fillMaxWidth()
            )

            var darkIsLoading by remember { mutableStateOf(false) }
            Surface(
                color = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Dark Theme Simulation", style = MaterialTheme.typography.bodySmall)
                    SliderButton(
                        onComplete = {
                            darkIsLoading = true
                        },
                        backgroundColor = Color.DarkGray,
                        contentColor = Color.White,
                        isLoading = darkIsLoading
                    )
                }
            }
        }
    }
}

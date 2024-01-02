package com.example.food2forkmvvm.presentation.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedRecipeDetailShimmer(
    isLoading: Boolean,
    contentAfterLoading: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    padding: Dp,
    cardHeight: Dp,
) {

    if (isLoading) {
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {

            Surface(shape = MaterialTheme.shapes.small) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .height(cardHeight)
                        .shimmerEffect()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(cardHeight / 10)
                    .shimmerEffect()

            )

            Spacer(modifier = Modifier.height(16.dp))
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(cardHeight / 10)
                    .shimmerEffect()

            )

            Spacer(modifier = Modifier.height(16.dp))
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(cardHeight / 10)
                    .shimmerEffect()

            )

        }


    } else {
        contentAfterLoading()
    }

}

@Composable
fun Modifier.shimmerDetailEffect(): Modifier {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val transition = rememberInfiniteTransition()
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1300, // duration for the animation
                delayMillis = 300, //delay
                easing = LinearEasing,
            )
        )
    )

    return composed {
        onGloballyPositioned {
            size = it.size
        }
    }.then(
        Modifier.background(
            brush = Brush.linearGradient(
                colors = listOf(
                    Color.LightGray.copy(alpha = 0.9f),
                    Color.LightGray.copy(alpha = 0.2f),
                    Color.LightGray.copy(alpha = 0.9f),
                ),
                start = Offset(startOffsetX, 0f),
                end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
            )
        )
    )
}


package com.example.mvvmrecipeapp.presentation.components.util

import android.util.FloatProperty
import androidx.compose.animation.core.*
import kotlin.math.tan

/**
 * Inspiration from:
 * https://github.com/Gurupreet/ComposeCookBook/blob/master/app/src/main/java/com/guru/composecookbook/ui/Animations/AnimationDefinitions.kt
 */
class ShimmerAnimationDefinitions(
    private val widthPx: Float,
    private val heightPx: Float,
    private val animationDuration: Int = 1300,
    private val animationDelay: Int = 300
) {
    var gradientWidth: Float = (0.2f * heightPx)

    enum class AnimationState {
        START, END
    }




}
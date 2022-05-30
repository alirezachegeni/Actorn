package com.mahdi.actorn.utils

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.borderRevealAnimation(
          initialState : Float = 30f ,
          size : Dp = 10.dp ,
          shape : Shape = CircleShape ,
) : Modifier = composed {
     val animate = remember { Animatable(initialState) }.apply {
          RevealEffectAnimation(this)
     }
     this
               .size(size)
               .clip(shape)
               .background(color = MaterialTheme.colors.surface)
               .border(
                         width = Dp(animate.value) ,
                         color = MaterialTheme.colors.onSurface ,
                         shape = shape
               )
}

@Composable
private fun RevealEffectAnimation(
          animateShape : Animatable<Float , AnimationVector1D> ,
          targetValue : Float = 1f ,
          durationMills : Int = 1000 ,
          delayMills : Int = 100 ,
          iteration : Int = 1 ,
) {
     LaunchedEffect(animateShape) {
          animateShape.animateTo(
                    targetValue = targetValue ,
                    animationSpec = repeatable(
                              animation = tween(
                                        durationMillis = durationMills ,
                                        easing = LinearEasing ,
                                        delayMillis = delayMills
                              ) ,
                              repeatMode = RepeatMode.Restart ,
                              iterations = iteration
                    )
          )
     }
}
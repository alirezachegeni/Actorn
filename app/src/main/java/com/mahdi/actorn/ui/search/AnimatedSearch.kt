package com.mahdi.actorn.ui.search

import android.annotation.SuppressLint
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun AnimateShapeInfinitely(
          animateShape : Animatable<Float , AnimationVector1D> ,
          targetValue : Float = 1f ,
          durationMills : Int = 1000 ,
)
{
     LaunchedEffect(animateShape) {
          animateShape.animateTo(
                    targetValue = targetValue ,
                    animationSpec = infiniteRepeatable(
                              animation = tween(
                                        durationMillis = durationMills ,
                                        easing = LinearEasing
                              ) ,
                              repeatMode = RepeatMode.Restart
                    )
          )
     }
}

@SuppressLint("RememberReturnType")
@Composable
fun AnimatedSearch()
{
     val animatedCircle = remember { Animatable(0f) }.apply {
          AnimateShapeInfinitely(this)
     }
     val animateLine = remember {
          Animatable(0.10f)
     }.apply { AnimateShapeInfinitely(this) }
     val surfaceColor = MaterialTheme.colors.surface
     Canvas(modifier = Modifier) {
          drawArc(
                    color = surfaceColor ,
                    startAngle = 50f ,
                    sweepAngle = 40f * animatedCircle.value ,
                    useCenter = false ,
                    size = Size(100f , 100f) ,
                    style = Stroke(20f , cap = StrokeCap.Round)
          )
          drawLine(
                    color = surfaceColor ,
                    strokeWidth = 20f ,
                    cap = StrokeCap.Round ,
                    start = Offset(
                              animateLine.value * 10f ,
                              animateLine.value * 10f
                    ) ,
                    end = Offset(
                              animateLine.value * 120f ,
                              animateLine.value * 120f
                    )
          )
     }
}
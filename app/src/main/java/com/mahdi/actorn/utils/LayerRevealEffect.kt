package com.mahdi.actorn.utils

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.mahdi.actorn.ui.theme.ActornTheme
import com.mahdi.actorn.R

@Composable
fun LayerRevealImage()
{
     ActornTheme(
               darkTheme = true
     ) {
          Surface(
                    color = MaterialTheme.colors.background ,
          ) {
               Spacer(
                         Modifier
                                   .fillMaxWidth()
                                   .statusBarsPadding()
                                   .navigationBarsPadding()
               )
               Image(
                         painterResource(id = R.drawable.animated_progress) ,
                         contentDescription = "" ,
                         modifier = Modifier.fillMaxSize() ,
                         contentScale = ContentScale.Crop
               )
               val animateShape = remember { Animatable(1f) }.also {
                    LaunchAnimation(it)
               }
               Canvas(
                         modifier = Modifier.fillMaxSize()
               ) {
                    drawRevealingRectangle(this , animateShape)
                    this.rotate(180f) {
                         drawRevealingRectangle(this , animateShape)
                    }
               }
          }
     }
}


fun DrawScope.drawRevealingRectangle(
          canvasScope : DrawScope ,
          animateShape : Animatable<Float , AnimationVector1D> ,
)
{
     drawRect(
               color = Color.LightGray ,
               topLeft = Offset(0f , 0f) ,
               size = Size(
                         width = canvasScope.size.width ,
                         height = canvasScope.size.height / 2 * animateShape.value
               ) ,
               alpha = 1f ,
               blendMode = BlendMode.Hue
     )
}

@Composable
fun LaunchAnimation(
          animateShape : Animatable<Float , AnimationVector1D> ,
)
{
     LaunchedEffect(animateShape) {
          animateShape.animateTo(
                    targetValue = 0f ,
                    animationSpec = repeatable(
                              animation = tween(
                                        durationMillis = 1500 ,
                                        easing = FastOutSlowInEasing ,
                                        delayMillis = 100
                              ) ,
                              repeatMode = RepeatMode.Restart ,
                              iterations = 1
                    )
          )
     }
}

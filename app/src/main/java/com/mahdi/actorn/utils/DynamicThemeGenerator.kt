package com.mahdi.actorn.utils

import android.content.Context
import androidx.annotation.FloatRange
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import coil.Coil
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.size.Scale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow

fun Modifier.verticalGradientScrim(
          color : Color ,
          @FloatRange(from = 0.0 , to = 1.0) startYPercentage : Float = 0f ,
          @FloatRange(from = 0.0 , to = 1.0) endYPercentage : Float = 1f ,
          decay : Float = 1.0f ,
          numStops : Int = 20 ,
) : Modifier = composed {
     val colors = remember(color , numStops) {
          if (decay != 1f) {
               val baseAlpha = color.alpha
               List(numStops) { i ->
                    val x = i * 1f / (numStops - 1)
                    val opacity = x.pow(decay)
                    color.copy(alpha = baseAlpha * opacity)
               }
          }
          else {
               listOf(color.copy(alpha = 0f) , color)
          }
     }
     
     var height by remember { mutableStateOf(0f) }
     val brush = remember(color , numStops , startYPercentage , endYPercentage , height) {
          Brush.verticalGradient(
                    colors = colors ,
                    startY = height * startYPercentage ,
                    endY = height * endYPercentage
          )
     }
     
     drawBehind {
          height = size.height
          drawRect(brush = brush)
     }
}

@Composable
private fun rememberDominantColorState(
          context : Context = LocalContext.current ,
          defaultColor : Color = MaterialTheme.colors.primary ,
          defaultOnColor : Color = MaterialTheme.colors.onPrimary ,
          isColorValid : (Color) -> Boolean = { true } ,
) : DominantColorState = remember {
     DominantColorState(context , defaultColor , defaultOnColor , isColorValid)
}

@Stable
class DominantColorState(
          private val context : Context ,
          private val defaultColor : Color ,
          private val defaultOnColor : Color ,
          private val isColorValid : (Color) -> Boolean = { true } ,
) {
     var color by mutableStateOf(defaultColor)
          private set
     var onColor by mutableStateOf(defaultOnColor)
          private set
     
     suspend fun updateColorsFromImageUrl(url : String) {
          val result = calculateDominantColor(url)
          color = result?.color ?: defaultColor
          onColor = result?.onColor ?: defaultOnColor
     }
     
     private suspend fun calculateDominantColor(url : String) : DominantColors? {
          return calculateSwatchesInImage(context , url)
                    .sortedByDescending { swatch -> swatch.population }
                    .firstOrNull { swatch -> isColorValid(Color(swatch.rgb)) }
                    ?.let { swatch ->
                         DominantColors(
                                   color = Color(swatch.rgb) ,
                                   onColor = Color(swatch.bodyTextColor).copy(alpha = 1f)
                         )
                    }
     }
}

@Immutable
private data class DominantColors(val color : Color , val onColor : Color)

@Composable
private fun DynamicThemePrimaryColorsFromImage(
          dominantColorState : DominantColorState = rememberDominantColorState() ,
          content : @Composable () -> Unit ,
) {
     val colors = MaterialTheme.colors.copy(
               primary = animateColorAsState(
                         dominantColorState.color ,
                         spring(stiffness = Spring.StiffnessLow)
               ).value ,
               onPrimary = animateColorAsState(
                         dominantColorState.onColor ,
                         spring(stiffness = Spring.StiffnessLow)
               ).value
     )
     MaterialTheme(colors = colors , content = content)
}

private suspend fun calculateSwatchesInImage(
          context : Context ,
          imageUrl : String ,
) : List<Palette.Swatch> {
     val r = ImageRequest.Builder(context)
               .data(imageUrl)
               .size(128).scale(Scale.FILL)
               .allowHardware(false)
               .build()
     
     val bitmap = when (val result = Coil.execute(r)) {
          is SuccessResult -> result.drawable.toBitmap()
          else             -> null
     }
     return bitmap?.let {
          withContext(Dispatchers.Default) {
               val palette = Palette.Builder(bitmap)
                         .resizeBitmapArea(0)
                         .clearFilters()
                         .maximumColorCount(8)
                         .generate()
               palette.swatches
          }
     } ?: emptyList()
}

@Composable
fun ActorDynamicTheme(
          podcastImageUrl : String ,
          content : @Composable () -> Unit ,
) {
     val backgroundColor = MaterialTheme.colors.background
     val dominantColorState = rememberDominantColorState(
               defaultColor = MaterialTheme.colors.background
     ) { color ->
          color.contrastAgainst(backgroundColor) >= MinContrastOfPrimaryVsBackground
     }
     DynamicThemePrimaryColorsFromImage(dominantColorState) {
          LaunchedEffect(podcastImageUrl) {
               dominantColorState.updateColorsFromImageUrl(podcastImageUrl)
          }
          content()
     }
}

private fun Color.contrastAgainst(background : Color) : Float {
     val fg = if (alpha < 1f) compositeOver(background) else this
     val fgLuminance = fg.luminance() + 0.05f
     val bgLuminance = background.luminance() + 0.05f
     return max(fgLuminance , bgLuminance) / min(fgLuminance , bgLuminance)
}

private const val MinContrastOfPrimaryVsBackground = 3f
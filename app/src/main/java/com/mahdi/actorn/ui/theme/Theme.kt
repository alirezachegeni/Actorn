package com.mahdi.actorn.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.mahdi.actorn.R

private val DarkColorPalette = darkColors(
          primary = dark_primary ,
          onPrimary = dark_onPrimary ,
          background = dark_background ,
          onBackground = dark_onBackground ,
          surface = dark_surface ,
          onSurface = dark_onSurface
)

private val LightColorPalette = lightColors(
          primary = light_primary ,
          onPrimary = light_onPrimary ,
          background = light_background ,
          onBackground = light_onBackground ,
          surface = light_surface ,
          onSurface = light_onSurface ,
)
 val LatoBold = FontFamily(
    Font(R.font.latobold),
)
val Lato = FontFamily(
    Font(R.font.lato)
)

@Composable
fun ActornTheme(darkTheme : Boolean = isSystemInDarkTheme() , content : @Composable () -> Unit)
{
     val colors = if (darkTheme)
     {
          DarkColorPalette
     }
     else
     {
          LightColorPalette
     }
     
     MaterialTheme(
               colors = colors ,
               typography = Typography ,
               shapes = Shapes ,
               content = content
     )
}
package com.mahdi.actorn.ui.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.mahdi.actorn.R

@Composable
fun DetailAppBar(
          navigateUp : () -> Unit ,
          title : String ,
)
{
     Box(modifier = Modifier.fillMaxWidth()) {
          IconButton(onClick = navigateUp , modifier = Modifier.align(Alignment.CenterStart)) {
               Icon(imageVector = Icons.Rounded.ArrowBack ,
                    contentDescription = stringResource(id = R.string.cd_up_button) ,
                    tint = MaterialTheme.colors.onBackground)
          }
     }
     Box {
          Text(text = title ,
              fontSize = 22.sp,
               color = MaterialTheme.colors.onBackground ,
               style = MaterialTheme.typography.h1 ,
               textAlign = TextAlign.Center ,
               modifier = Modifier
                         .fillMaxWidth()
                         .align(Alignment.Center)
          )
     }
     
}
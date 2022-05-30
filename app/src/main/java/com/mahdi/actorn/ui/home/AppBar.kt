package com.mahdi.actorn.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun HomeAppBar(
          navigateToSearch : () -> Unit ,
)
{
    Column {

        Spacer(modifier = Modifier.height(30.dp))
     Row(verticalAlignment = Alignment.CenterVertically ,
         modifier = Modifier
             .fillMaxWidth()
             .height(50.dp)
             .clip(shape = MaterialTheme.shapes.medium)
             .clickable(onClick = navigateToSearch)
             .background(color = Color(0xFF37474f))) {
          Spacer(modifier = Modifier.padding(horizontal = 10.dp))
          Icon(
                    imageVector = Icons.Default.Search ,
                    contentDescription = stringResource(id =com.mahdi.actorn.R.string.cd_search_icon) ,
                    tint = MaterialTheme.colors.onSurface , modifier = Modifier.alpha(0.5f)
          )
          Spacer(modifier = Modifier.padding(horizontal = 15.dp))
          Text(text = stringResource(id =com.mahdi.actorn. R.string.search_app_bar_title) ,
               color = MaterialTheme.colors.onSurface ,
               style = MaterialTheme.typography.subtitle1 ,
               modifier = Modifier.alpha(0.5f)
          )
     }
}}
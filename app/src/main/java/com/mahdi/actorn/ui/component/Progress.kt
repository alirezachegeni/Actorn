package com.mahdi.actorn.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mahdi.actorn.ui.search.AnimatedSearch
import com.mahdi.actorn.utils.InfinitelyFlowingCircles

@Composable
fun ShowProgressIndicator(
          isLoadingData : Boolean ,
) {
     if (isLoadingData) {
          Box(
                    modifier = Modifier.fillMaxSize()
          ) {
               CircularProgressIndicator(
                         modifier = Modifier.align(Alignment.Center) ,
                         color = MaterialTheme.colors.onBackground
               )
          }
     }
}

@Composable
fun ShowSearchProgress(
          isLoadingData : Boolean ,
) {
     if (isLoadingData) {
          InfinitelyFlowingCircles()
          Box(
                    modifier = Modifier
                              .fillMaxSize()
                              .padding(bottom = 30.dp , end = 30.dp) ,
                    contentAlignment = Alignment.Center
          ) {
               AnimatedSearch()
          }
     }
}
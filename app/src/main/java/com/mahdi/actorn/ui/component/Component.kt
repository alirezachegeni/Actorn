package com.mahdi.actorn.ui.component

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mahdi.actorn.utils.ApiKey
import com.mahdi.actorn.utils.NetworkManger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
private fun LaunchSnackBar(
          scaffoldState : ScaffoldState ,
          snackBarMessage : String ,
          scope : CoroutineScope = rememberCoroutineScope() ,
) {
     LaunchedEffect(scope) {
          scope.launch {
               scaffoldState.snackbarHostState.showSnackbar(
                         message = snackBarMessage ,
                         duration = SnackbarDuration.Indefinite
               )
          }
     }
}


@Composable
fun IfOfflineShowSnackBar(
          scaffoldState : ScaffoldState ,
          context : Context = LocalContext.current ,
) {
     val isOnline = NetworkManger(context).checkForActivityNetwork()
     if (! isOnline) {
          LaunchSnackBar(
                    scaffoldState ,
                  "You Are Offline!"
          )
     }
}


@Composable
fun ApiKeyMissingShowSnackBar(
          scaffoldState : ScaffoldState ,
          context : Context = LocalContext.current ,
) {
     if (ApiKey.API_KEY.isEmpty()) {
          LaunchSnackBar(
                    scaffoldState ,
                   "Tmdb Api Key Missing"
          )
     }
}

@Composable
fun AppDivider(
          verticalPadding : Dp ,
) {
     Divider(
               color = MaterialTheme.colors.onBackground.copy(alpha = 0.1f) ,
               thickness = 1.dp ,
               startIndent = 0.dp ,
               modifier = Modifier.padding(vertical = verticalPadding)
     )
}

@Composable
fun CategoryTitle(
          title : String ,
) {

     Text(
               text = title ,
         style = MaterialTheme.typography.h2 ,
               color = Color.White ,
               modifier = Modifier
                         .padding(start = 20.dp).alpha(0.75f)
                         ,
         fontSize = 20.sp
     )
}
@Composable
fun CategoryHome(
    title: String
){

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
        Text(
            text = title ,
            style = MaterialTheme.typography.h1 ,
            color = Color.White ,
            modifier = Modifier
                .alpha(0.75f)
            ,
            fontSize = 22.sp
        )
    }
}
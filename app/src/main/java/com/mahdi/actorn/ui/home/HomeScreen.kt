package com.mahdi.actorn.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.mahdi.actorn.R
import com.mahdi.actorn.model.Actor
import com.mahdi.actorn.ui.component.*

@Composable
fun HomeScreen(
          selectedActor : (Int) -> Unit ,
          navigationToSearch : () -> Unit ,
          viewModel : HomeViewModel ,
)
{
     val uiState = viewModel.uiState
     val scaffoldState = rememberScaffoldState()
     Surface(
               color = MaterialTheme.colors.background ,
     ) {
          Scaffold(
                    scaffoldState = scaffoldState ,
                    topBar = { MainAppBar(navigationToSearch) } ,
                    snackbarHost = { hostState ->
                         SnackbarHost(hostState) { data ->
                              Snackbar(
                                        snackbarData = data ,
                                        modifier = Modifier.padding(bottom = 50.dp)
                              )
                         }
                    }
          ) {
               Box {
                    ShowProgressIndicator(isLoadingData = uiState.isFetchingActors)
                    ScreenContent(selectedActor , uiState)
                    IfOfflineShowSnackBar(scaffoldState)
                    ApiKeyMissingShowSnackBar(scaffoldState)
               }
          }
     }
}

@Composable
private fun ScreenContent(
          selectedActor : (Int) -> Unit ,
          viewState : HomeViewState ,
)
{
     LazyColumn {
          item {
               Spacer(modifier = Modifier.height(40.dp))
          }
          item {
               CategoryHome(stringResource(id = R.string.category_actors_popular))
          }
          item {
               Spacer(modifier = Modifier.padding(vertical = 10.dp))
          }
          item {
               ItemActorList(
                         actorsList = viewState.popularActorList ,
                         selectedActor = selectedActor
               )
          }
          item {
               AppDivider(verticalPadding = 30.dp)
          }
          item {
               CategoryHome(title = stringResource(id = R.string.category_actors_trending))
          }
          item {
               Spacer(modifier = Modifier.padding(vertical = 10.dp))
          }
          item {
               ItemActorList(
                         actorsList = viewState.trendingActorList ,
                         selectedActor = selectedActor
               )
          }
          item {
               Spacer(modifier = Modifier.padding(vertical = 30.dp))
          }
     }
}

@Composable
private fun ItemActorList(
          actorsList : List<Actor> ,
          selectedActor : (Int) -> Unit ,
)
{
     LazyRow(
               horizontalArrangement = Arrangement.spacedBy(10.dp) ,
               modifier = Modifier.padding(start = 20.dp)
     ) {
          items(actorsList) { actor ->
               ItemActor(actor = actor , selectedActor = selectedActor)
          }
     }
}

@Composable
private fun ItemActor(
          actor : Actor ,
          selectedActor : (Int) -> Unit ,
)
{
     Card(
               modifier = Modifier
                   .width(150.dp)
                   .clickable(onClick = { selectedActor(actor.actorId) }) ,
               shape = RoundedCornerShape(15.dp)
     ) {
         Box(modifier = Modifier.background(Color(0xFF37474f))) {


             Column(horizontalAlignment = Alignment.CenterHorizontally) {
                 Spacer(modifier = Modifier.padding(vertical = 10.dp))
                 LoadNetworkImage(
                     imageUrl = actor.profileUrl,
                     contentDescription = stringResource(id = R.string.cd_actor_image),
                     modifier = Modifier
                         .size(120.dp)
                         .border(
                             width = 1.dp,
                             color = MaterialTheme.colors.onSurface,
                             shape = CircleShape
                         ),
                     shape = CircleShape
                 )
                 Spacer(modifier = Modifier.padding(vertical = 10.dp))
                 Text(
                     text = actor.actorName,
                     color = Color.White,
                     style = MaterialTheme.typography.h2,
                     textAlign = TextAlign.Center,
                     maxLines = 1,
                     overflow = TextOverflow.Ellipsis,
                     modifier = Modifier
                         .padding(horizontal = 10.dp)
                         .fillMaxWidth()
                 )
                 Spacer(modifier = Modifier.padding(vertical = 10.dp))
             }
         }
     }
}

@Composable
private fun MainAppBar(
          navigationToSearch : () -> Unit ,
)
{
     TopAppBar(

               content = {
                    HomeAppBar(navigationToSearch)
               } ,
               backgroundColor = MaterialTheme.colors.background ,
               elevation = 0.dp ,
               modifier = Modifier.padding(horizontal = 20.dp).height(120.dp) ,
               contentPadding = rememberInsetsPaddingValues(
                         insets = LocalWindowInsets.current.statusBars)
     )
}

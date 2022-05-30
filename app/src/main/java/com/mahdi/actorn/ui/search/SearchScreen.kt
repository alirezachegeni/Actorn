package com.mahdi.actorn.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsHeight
import com.mahdi.actorn.R
import com.mahdi.actorn.model.Actor
import com.mahdi.actorn.ui.component.AppDivider
import com.mahdi.actorn.ui.component.ShowProgressIndicator

@Composable
fun SearchScreen(
          selectedActor : (Int) -> Unit ,
          viewModel : SearchViewModel ,
          navigateUp : () -> Unit ,
)
{
     val uiState = viewModel.uiState
     Surface(color = MaterialTheme.colors.background) {
          Scaffold(topBar = { SearchAppBar(navigateUp = navigateUp , viewModel = viewModel) }) {
               Box {
                    val isLoadingData = uiState.isSearchingResults
                    ShowProgressIndicator(isLoadingData = isLoadingData)
                    ItemActorList(actorList = uiState.actorList , selectedActor = selectedActor)
               }
          }
     }
}

@Composable
private fun ItemActorList(
          actorList : List<Actor> ,
          selectedActor : (Int) -> Unit ,
)
{
     LazyColumn(modifier = Modifier.padding(bottom = 50.dp)) {
          items(actorList) { actor ->
               ItemActor(actor = actor , selectedActor = selectedActor)
          }
     }
}

@Composable
fun ItemActor(
          actor : Actor ,
          selectedActor : (Int) -> Unit ,
)
{
     Text(text = actor.actorName ,
          style = MaterialTheme.typography.h6 ,
          color = MaterialTheme.colors.onBackground ,
          modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = { selectedActor(actor.actorId) })
                    .padding(horizontal = 20.dp , vertical = 10.dp)
                    //age esm toolani bashe mishkanash
                    .wrapContentWidth(align = Alignment.Start))
}

@Composable
fun SearchAppBar(
          navigateUp : () -> Unit ,
          viewModel : SearchViewModel ,
)
{
     var query : String by rememberSaveable {
          mutableStateOf("")
     }
     
     var showClearQueryIcon : Boolean by rememberSaveable {
          mutableStateOf(false)
     }
     
     if (query.isEmpty())
     {
          showClearQueryIcon = false
     }
     else if (query.isNotEmpty())
     {
          showClearQueryIcon = true
     }
     
     Column {
          Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsHeight())
          TextField(value = query ,
                    onValueChange = { onQueryChange ->
                         query = onQueryChange
                         if (onQueryChange.isNotEmpty())
                         {
                              viewModel.performQuery(onQueryChange)
                         }
                    } ,
                    leadingIcon = {
                         IconButton(onClick = navigateUp) {
                              Icon(imageVector = Icons.Rounded.ArrowBack ,
                                   contentDescription = stringResource(
                                             id = R.string.cd_search_icon) ,
                                   tint = MaterialTheme.colors.onBackground)
                         }
                    } ,
                    trailingIcon = {
                         if (showClearQueryIcon)
                         {
                              IconButton(onClick = { query = "" }) {
                                   Icon(imageVector = Icons.Rounded.Clear ,
                                        contentDescription = stringResource(
                                                  id = R.string.cd_clear_icon) ,
                                        tint = MaterialTheme.colors.onBackground)
                              }
                         }
                    } ,
                    placeholder = {
                         Text(text = stringResource(R.string.hint_search_query))
                    } ,
                    textStyle = MaterialTheme.typography.subtitle1 ,
                    singleLine = true ,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text) ,
                    maxLines = 1 ,
                    modifier = Modifier
                              .fillMaxWidth()
                              .background(Color(0xFF37474f) , RectangleShape) ,
              colors = TextFieldDefaults.textFieldColors(
                  textColor = Color.White ,
                  disabledTextColor = Color(0xFF37474f) ,
                  backgroundColor = Color(0xFF37474f) ,
                  focusedIndicatorColor = Color(0xFF37474f) ,
                  unfocusedIndicatorColor = Color(0xFF37474f) ,
                  disabledIndicatorColor = Color(0xFF37474f)
              ))
          
          AppDivider(verticalPadding = 0.dp)
     }
}
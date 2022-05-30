package com.mahdi.actorn.navigation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mahdi.actorn.ui.details.DetailsScreen
import com.mahdi.actorn.ui.details.DetailsViewModel
import com.mahdi.actorn.ui.home.HomeScreen
import com.mahdi.actorn.ui.home.HomeViewModel
import com.mahdi.actorn.ui.movie_detail.MovieDetailScreen
import com.mahdi.actorn.ui.movie_detail.MovieDetailViewModel
import com.mahdi.actorn.ui.search.SearchScreen
import com.mahdi.actorn.ui.search.SearchViewModel
import com.mahdi.actorn.ActornApp

@Composable
fun AppNavigation(
    startDestination : String = AppDestinations.HOME_ROUTE,
    routes : AppDestinations = AppDestinations,
) {
     val navController = rememberNavController()
     val actions = remember(navController) {
          AppActions(navController , routes)
     }
     val application = LocalContext.current.applicationContext as Application
     val repository = (application as ActornApp).repository
     NavHost(
               navController = navController ,
               startDestination = startDestination
     ) {
          
          composable(
                    AppDestinations.HOME_ROUTE
          ) {
               HomeScreen(
                         selectedActor = actions.selectedActor ,
                         navigationToSearch = actions.navigateToSearch ,
                         viewModel = viewModel(
                                   factory = HomeViewModel.provideFactory(
                                             application , repository
                                   )
                         )
               )
          }
          composable(
                    AppDestinations.SEARCH_ROUTE
          ) {
               SearchScreen(
                         selectedActor = actions.selectedActor ,
                         navigateUp = actions.navigateUp ,
                         viewModel = viewModel(
                                   factory = SearchViewModel.provideFactory(
                                             application , repository
                                   )
                         )
               )
          }
          composable(
                    route = "${AppDestinations.ACTOR_DETAIL_ROUTE}/{${routes.ACTOR_DETAIL_ID_KEY}}" ,
                    arguments = listOf(
                              navArgument(
                                        routes.ACTOR_DETAIL_ID_KEY
                              ) {
                                   type = NavType.IntType
                              })
          ) { backStackEntry ->
               val arguments = requireNotNull(backStackEntry.arguments)
               val actorId = arguments.getInt(routes.ACTOR_DETAIL_ID_KEY)
               DetailsScreen(
                         selectedMovie = actions.selectedMovie ,
                         navigateUp = actions.navigateUp ,
                         viewModel = viewModel(
                                   factory = DetailsViewModel.provideFactory(
                                             application,actorId,repository
                                   )
                         )
               )
          }
          
          composable(
                    route = "${AppDestinations.MOVIE_DETAILS_ROUTE}/{${routes.MOVIE_DETAILS_ID_KEY}}" ,
                    arguments = listOf(
                              navArgument(
                                        routes.MOVIE_DETAILS_ID_KEY
                              ) {
                                   type = NavType.IntType
                              })
          ) { backStackEntry ->
               val arguments = requireNotNull(backStackEntry.arguments)
               val movieId = arguments.getInt(routes.MOVIE_DETAILS_ID_KEY)
               MovieDetailScreen(
                         navigateUp = actions.navigateUp ,
                         viewModel = viewModel(
                                   factory = MovieDetailViewModel.provideFactory(
                                             application, repository, movieId
                                   )
                         ) ,
                         selectedMovie = actions.selectedMovie
               )
          }
     }
}
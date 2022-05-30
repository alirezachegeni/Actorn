package com.mahdi.actorn.navigation

import androidx.navigation.NavHostController

class AppActions(
          private val navController : NavHostController ,
          private val routes : AppDestinations ,
)
{
     val selectedActor : (Int) -> Unit = { actorId : Int ->
          navController.navigate("${routes.ACTOR_DETAIL_ROUTE}/$actorId")
     }
     
     val selectedMovie : (Int) -> Unit = { movieId : Int ->
          navController.navigate("${routes.MOVIE_DETAILS_ROUTE}/$movieId")
     }
     val navigateToSearch = {
          navController.navigate(routes.SEARCH_ROUTE)
     }
     val navigateUp : () -> Unit = {
          navController.navigateUp()
     }
}
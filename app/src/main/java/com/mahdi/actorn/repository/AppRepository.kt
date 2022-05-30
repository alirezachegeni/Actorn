package com.mahdi.actorn.repository

import com.mahdi.actorn.data.NetworkDataSource
import com.mahdi.actorn.model.Actor
import com.mahdi.actorn.model.ActorDetail
import com.mahdi.actorn.model.Movie
import com.mahdi.actorn.model.MovieDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppRepository(
          private val source : NetworkDataSource ,
)
{
     suspend fun getPopularActorsData() : List<Actor>
     {
          val popularActorsList : List<Actor>
          withContext(Dispatchers.IO) {
               popularActorsList = source.getPopularActors()
          }
          return popularActorsList
     }
     
     suspend fun getTrendingActorsData() : List<Actor>
     {
          val trendingActorsList : List<Actor>
          withContext(Dispatchers.IO) {
               trendingActorsList = source.getTrendingActors()
          }
          return trendingActorsList
     }
     
     suspend fun getSelectedActorData(
               actorId : Int ,
     ) : ActorDetail
     {
          val selectedActorDetails : ActorDetail
          withContext(Dispatchers.IO) {
               selectedActorDetails = source.getActorDetails(actorId)
          }
          return selectedActorDetails
     }
     
     suspend fun getCastData(
               actorId : Int ,
     ) : List<Movie>
     {
          val castListData : List<Movie>
          withContext(Dispatchers.IO) {
               castListData = source.getCastDetails(actorId)
          }
          return castListData
     }
     
     suspend fun getSearchableActorsData(
               query : String ,
     ) : List<Actor>
     {
          val searchableActorsList : List<Actor>
          withContext(Dispatchers.IO) {
               searchableActorsList = source.getSearchableActors(query)
          }
          return searchableActorsList
     }
     
     suspend fun getSelectedMovieData(
               movieId : Int ,
     ) : MovieDetail
     {
          val selectedMovieDetails : MovieDetail
          withContext(Dispatchers.IO) {
               selectedMovieDetails = source.getMovieDetailsById(movieId)
          }
          return selectedMovieDetails
     }
}
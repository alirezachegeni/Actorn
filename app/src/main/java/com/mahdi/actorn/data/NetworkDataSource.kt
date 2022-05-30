package com.mahdi.actorn.data

import com.mahdi.actorn.model.Actor
import com.mahdi.actorn.model.ActorDetail
import com.mahdi.actorn.model.Movie
import com.mahdi.actorn.model.MovieDetail
import com.mahdi.actorn.utils.NetworkQueryUtils



class NetworkDataSource
{
     private val requestUrls = RequestUrls
     private val jsonData by lazy { JsonRemoteData(requestUrls) }
     private val queryUtils by lazy { NetworkQueryUtils() }
     
     fun getPopularActors() : List<Actor>
     {
          val requestUrl = requestUrls.getPopularActorsUrl()
          val response = queryUtils.getResponseFromHttpUrl(requestUrl)
          return jsonData.fetchActorsJsonData(response)
     }
     
     fun getTrendingActors() : List<Actor>
     {
          val requestUrl = requestUrls.getTrendingActorsUrl()
          val response = queryUtils.getResponseFromHttpUrl(requestUrl)
          return jsonData.fetchActorsJsonData(response)
     }
     
     fun getActorDetails(
               actorId : Int ,
     ) : ActorDetail
     {
          val requestUrl = requestUrls.getActorDetailsUrl(actorId)
          val response = queryUtils.getResponseFromHttpUrl(requestUrl)
          return jsonData.fetchActorDetailsJsonData(response)
     }
     
     fun getCastDetails(
               actorId : Int ,
     ) : List<Movie>
     {
          val requestUrl = requestUrls.getCastDetailsUrl(actorId)
          val response = queryUtils.getResponseFromHttpUrl(requestUrl)
          return jsonData.fetchCastDetailsJsonData(response)
     }
     
     fun getSearchableActors(
               query : String ,
     ) : List<Actor>
     {
          val requestUrl = requestUrls.getSearchActorsUrl(query)
          val response = queryUtils.getResponseFromHttpUrl(requestUrl)
          return jsonData.fetchActorsJsonData(response)
     }
     
     fun getMovieDetailsById(
               movieId : Int ,
     ) : MovieDetail
     {
          val requestUrl = requestUrls.getMovieDetailsUrl(movieId)
          val response = queryUtils.getResponseFromHttpUrl(requestUrl)
          return jsonData.fetchMovieDetailsJsonData(response)
     }
}
package com.mahdi.actorn.data


import com.mahdi.actorn.utils.ApiKey
import java.net.URI
import java.net.URL

object RequestUrls
{
     private const val BASE_URL = "https://api.themoviedb.org/3/"
     private const val API_KEY = "api_key=${ApiKey.API_KEY}"
     const val LOW_RES_IMAGE = "https://image.tmdb.org/t/p/w200"
     const val HIGH_RES_IMAGE = "https://image.tmdb.org/t/p/w500"
     
     fun getPopularActorsUrl() : URI
     {
          return URI("${BASE_URL}person/popular?$API_KEY")
     }
     
     fun getTrendingActorsUrl() : URI
     {
          return URI("${BASE_URL}trending/person/week?$API_KEY")
     }
     
     fun getActorDetailsUrl(actorId : Int) : URI
     {
          return URI("${BASE_URL}person/${actorId}?$API_KEY")
     }
     
     fun getCastDetailsUrl(actorId : Int) : URI
     {
          return URI("${BASE_URL}person/${actorId}/movie_credits?$API_KEY")
     }
     
     fun getSearchActorsUrl(query : String) : URI
     {
          return URI("${BASE_URL}search/person?$API_KEY&query=$query")
     }
     
     fun getMovieDetailsUrl(movieId : Int) : URI
     {
          return URI("${BASE_URL}movie/$movieId?$API_KEY")
     }
     
     fun getMovieTrailerUrl(movieId : Int) : URL
     {
          return URL("${BASE_URL}movie/$movieId/videos?$API_KEY")
     }
     
     fun buildMovieTrailerThumbnail(trailerId : String) : URL
     {
          return URL("https://img.youtube.com/vi/$trailerId/sddefault.jpg")
     }
}
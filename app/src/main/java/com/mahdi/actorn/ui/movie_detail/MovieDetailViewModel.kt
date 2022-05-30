package com.mahdi.actorn.ui.movie_detail

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.mahdi.actorn.model.MovieDetail
import com.mahdi.actorn.repository.AppRepository
import kotlinx.coroutines.launch
import okio.IOException
import timber.log.Timber

class MovieDetailViewModel(
          application : Application ,
          private val appRepository : AppRepository ,
          private val movieId : Int ,
) : AndroidViewModel(application)
{
     var uiState by mutableStateOf(MovieDetailViewState(movieData = null))
          private set
     
     init
     {
          viewModelScope.launch {
               try
               {
                    startFetchingDetails()
               }
               catch (e : IOException)
               {
                    Timber.e(e)
               }
          }
     }
     
     private suspend fun startFetchingDetails()
     {
          uiState = MovieDetailViewState(
                    isRefrsh = true,
                    movieData = null
          )
          val movieData = appRepository.getSelectedMovieData(movieId = movieId)
          uiState = MovieDetailViewState(
                    movieData = movieData,
                    isRefrsh = false
          )
     }
     
     companion object
     {
          fun provideFactory(
                    application : Application ,
                    repository : AppRepository ,
                    movieId : Int ,
          ) : ViewModelProvider.AndroidViewModelFactory
          {
               return object : ViewModelProvider.AndroidViewModelFactory()
               {
                    @Suppress("UNCHECKED_CAST")
                    override fun <T : ViewModel> create(
                              modelClass : Class<T> ,
                              extras : CreationExtras
                    ) : T
                    {
                         if (modelClass.isAssignableFrom(MovieDetailViewModel::class.java))
                         {
                              return MovieDetailViewModel(
                                        movieId = movieId ,
                                        application = application ,
                                        appRepository = repository
                              ) as T
                         }
                         throw IllegalArgumentException("cannot create viewModel")
                    }
               }
          }
     }
}

data class MovieDetailViewState(
          val movieData : MovieDetail? ,
          val isRefrsh:Boolean = false
)
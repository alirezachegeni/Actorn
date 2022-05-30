package com.mahdi.actorn.ui.home

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.mahdi.actorn.model.Actor
import com.mahdi.actorn.repository.AppRepository
import kotlinx.coroutines.launch
import okio.IOException
import timber.log.Timber

class HomeViewModel(
          application : Application ,
          private val repository : AppRepository ,
) : AndroidViewModel(application) {
     var uiState by mutableStateOf(HomeViewState())
          private set
     
     init {
          viewModelScope.launch {
               try {
                    startFetchingActors()
               }
               catch (e : IOException) {
                    Timber.e("$e")
               }
          }
     }
     
     private suspend fun startFetchingActors() {
          uiState = HomeViewState(isFetchingActors = true)
          val trendingActorList = repository.getTrendingActorsData()
          val popularActorList = repository.getPopularActorsData()
          uiState = HomeViewState(
                    popularActorList = popularActorList ,
                    trendingActorList = trendingActorList ,
                    isFetchingActors = false
          )
     }
     
     companion object {
          fun provideFactory(
                    application : Application ,
                    repository : AppRepository ,
          ) : ViewModelProvider.AndroidViewModelFactory {
               return object : ViewModelProvider.AndroidViewModelFactory(application) {
                    @Suppress("UNCHECKED_CAST")
                    override fun <T : ViewModel> create(
                              modelClass : Class<T> ,
                              extras : CreationExtras
                    ) : T
                    {
                         if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                              return HomeViewModel(application , repository) as T
                         }
                         throw  IllegalArgumentException("Cannot Create ViewModel")
                    }
               }
          }
     }
     
}

data class HomeViewState(
          var popularActorList : List<Actor> = emptyList() ,
          var trendingActorList : List<Actor> = emptyList() ,
          val isFetchingActors : Boolean = false ,
)
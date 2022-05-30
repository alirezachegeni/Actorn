package com.mahdi.actorn.ui.details

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.mahdi.actorn.model.ActorDetail
import com.mahdi.actorn.model.Movie
import com.mahdi.actorn.repository.AppRepository
import kotlinx.coroutines.launch
import okio.IOException
import timber.log.Timber

class DetailsViewModel(
          application : Application ,
          private val actorId : Int ,
          private val repository : AppRepository ,
) : AndroidViewModel(application)
{
     var uiState by mutableStateOf(DetailsViewState(actorData = null))
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
          uiState = DetailsViewState(
                    isFetchingDetail = true ,
                    actorData = null
          )
          val actorData = repository.getSelectedActorData(actorId)
          val castData = repository.getCastData(actorId)
          uiState = DetailsViewState(castList = castData ,
                                     actorData = actorData ,
                                     isFetchingDetail = false)
     }
     
     
     companion object
     {
          fun provideFactory(
                    application : Application ,
                    actorId : Int ,
                    repository : AppRepository ,
          ) : ViewModelProvider.AndroidViewModelFactory
          {
               return object : ViewModelProvider.AndroidViewModelFactory(application = application)
               {
                    override fun <T : ViewModel> create(
                              modelClass : Class<T> ,
                              extras : CreationExtras
                    ) : T
                    {
                         if (modelClass.isAssignableFrom(DetailsViewModel::class.java))
                         {
                              return DetailsViewModel(application = application ,
                                                      actorId = actorId ,
                                                      repository = repository) as T
                         }
                         throw IllegalArgumentException("cannot create view model")
                    }
               }
          }
     }
}

data class DetailsViewState(
          val castList : List<Movie> = listOf() ,
          val actorData : ActorDetail? ,
          val isFetchingDetail : Boolean = false ,
)
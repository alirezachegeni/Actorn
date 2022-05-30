package com.mahdi.actorn.ui.search

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

class SearchViewModel(
          application : Application ,
          private val repository : AppRepository ,
) : AndroidViewModel(application) {
     var uiState by mutableStateOf(SearchViewState())
          private set
     
     fun performQuery(
               query : String ,
     ) {
          viewModelScope.launch {
               uiState = SearchViewState(isSearchingResults = true)
               val searchData = repository.getSearchableActorsData(query)
               uiState = uiState.copy(
                         actorList = searchData ,
                         isSearchingResults = false
               )
          }
     }
     
     companion object {
          fun provideFactory(
                    application : Application ,
                    repository : AppRepository ,
          ) : ViewModelProvider.AndroidViewModelFactory {
               return object : ViewModelProvider.AndroidViewModelFactory(application) {
                    @Suppress("unchecked_cast")
                    override fun <T : ViewModel> create(
                              modelClass : Class<T> ,
                              extras : CreationExtras
                    ) : T
                    {
                         if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
                              return SearchViewModel(application , repository) as T
                         }
                         throw IllegalArgumentException("Cannot create ViewModel")
                    }
               }
          }
     }
}

data class SearchViewState(
          val actorList : List<Actor> = emptyList() ,
          val isSearchingResults : Boolean = false ,
)
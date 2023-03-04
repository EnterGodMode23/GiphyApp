package com.example.giphyapp.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giphyapp.data.api.GifsRepository
import com.example.giphyapp.domain.models.Gifs
import com.example.giphyapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: GifsRepository): ViewModel() {

    val gifsLiveData = MutableLiveData<Resource<Gifs>>()
    val searchedGifsLiveData = MutableLiveData<Resource<Gifs>>()

    init {
        getGifs()
    }

    private fun getGifs() {
        viewModelScope.launch {
            val response = repository.getTrends()
            if (response.isSuccessful) {
                response.body().let {
                    gifsLiveData.postValue(Resource.Success(it))
                }
            }
        }
    }

    fun searchGifs(query: String) {
        viewModelScope.launch {
            val response = repository.getSearched(query = query)
            if (response.isSuccessful) {
                response.body().let {
                    searchedGifsLiveData.postValue(Resource.Success(it))
                }
            }
        }
    }
}
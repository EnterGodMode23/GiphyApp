package com.example.giphyapp.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giphyapp.data.retrofit.RetrofitClient
import com.example.giphyapp.domain.models.Data
import com.example.giphyapp.domain.models.Gifs
import com.example.giphyapp.utils.Resource
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val gifsLiveData = MutableLiveData<Resource<Gifs>>()
    val searchedGifsLiveData = MutableLiveData<Resource<Gifs>>()
    private val client = RetrofitClient.getRetrofit()

    init {
        getGifs()
    }

    private fun getGifs() {
        viewModelScope.launch {
            val response = client.getTrends()
            if (response.isSuccessful) {
                response.body().let {
                    gifsLiveData.postValue(Resource.Success(it))
                }
            }
        }
    }

    fun searchGifs(query: String) {
        viewModelScope.launch {
            val response = client.getSearched(query = query)
            if (response.isSuccessful) {
                response.body().let {
                    searchedGifsLiveData.postValue(Resource.Success(it))
                }
            }
        }
    }
}
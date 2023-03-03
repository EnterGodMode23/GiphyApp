package com.example.giphyapp.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giphyapp.data.retrofit.RetrofitClient
import com.example.giphyapp.domain.models.Data
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val gifsLiveData = MutableLiveData<List<Data>?>()
    val searchedGifsLiveData = MutableLiveData<List<Data>?>()
    private val client = RetrofitClient.getRetrofit()

    init {
        getGifs()
    }

    private fun getGifs() {
        viewModelScope.launch {
            val data = client.getTrends().body()?.data
            if (data != null) {
                gifsLiveData.postValue(data)
            }
        }
    }

    fun searchGifs(query: String) {
        viewModelScope.launch {
            val data = client.getSearched(query = query).body()?.data
            if (data != null) {
                searchedGifsLiveData.postValue(data)
            }
        }
    }
}
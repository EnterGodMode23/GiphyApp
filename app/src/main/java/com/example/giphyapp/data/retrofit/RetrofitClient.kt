package com.example.giphyapp.data.retrofit

import com.example.giphyapp.data.api.GifService
import com.example.giphyapp.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    fun getRetrofit(): GifService {
        val client = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(GifService::class.java)
        return client
    }

}
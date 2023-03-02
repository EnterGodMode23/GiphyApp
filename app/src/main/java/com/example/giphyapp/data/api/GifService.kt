package com.example.giphyapp.data.api

import com.example.giphyapp.domain.models.Gifs
import com.example.giphyapp.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GifService {

    @GET("v1/gifs/trending")
    suspend fun getTrends(
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("limit") limit: Int = Constants.LIMIT,
        @Query("rating") rating: String = Constants.RATING
    ) : Response<Gifs>

}
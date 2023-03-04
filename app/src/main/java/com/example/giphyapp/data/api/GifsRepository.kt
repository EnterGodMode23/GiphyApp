package com.example.giphyapp.data.api

import javax.inject.Inject

class GifsRepository @Inject constructor(private val gifService: GifService) {

    suspend fun getTrends() = gifService.getTrends()

    suspend fun getSearched(query: String) = gifService.getSearched(query = query)
}
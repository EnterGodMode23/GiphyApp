package com.example.giphyapp.data.di

import com.example.giphyapp.data.api.GifService
import com.example.giphyapp.utils.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun baseUrl() = BASE_URL

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String): GifService =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GifService::class.java)
}
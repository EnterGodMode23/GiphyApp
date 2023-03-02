package com.example.giphyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.giphyapp.data.adapters.GifAdapter
import com.example.giphyapp.data.api.GifService
import com.example.giphyapp.data.retrofit.RetrofitClient
import com.example.giphyapp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: GifAdapter
    private lateinit var service: GifService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = GifAdapter()
        binding.rv.layoutManager = LinearLayoutManager(this)
        binding.rv.adapter = adapter

        service = RetrofitClient.getRetrofit()


        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getTrends().body()?.data
            if (response != null) {
                adapter.gifs = response
            }
        }

    }
}
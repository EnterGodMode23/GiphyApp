package com.example.giphyapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.giphyapp.data.adapters.GifAdapter
import com.example.giphyapp.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: GifAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rv.layoutManager = LinearLayoutManager(this)
        viewModel = MainViewModel()
        adapter = GifAdapter()

        binding.rv.adapter = adapter.apply {
            viewModel.gifsLiveData.observe(this@MainActivity, Observer {
                this.differ.submitList(it)
            })
        }
        binding.rv.adapter = adapter.apply {
            viewModel.searchedGifsLiveData.observe(this@MainActivity, Observer {
                this.differ.submitList(it)
            })
        }

        val searchField = binding.edSearch
        var job: Job? = null
        searchField.addTextChangedListener{
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                it?.let {
                    if (it.toString().isNotEmpty()){
                        viewModel.searchGifs(it.toString())
                    }
                }
            }
        }

    }
}
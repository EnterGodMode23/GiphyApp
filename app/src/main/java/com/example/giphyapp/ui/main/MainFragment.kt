package com.example.giphyapp.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.giphyapp.R
import com.example.giphyapp.data.adapters.GifAdapter
import com.example.giphyapp.databinding.FragmentMainBinding
import com.example.giphyapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var adapter: GifAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rv.layoutManager = LinearLayoutManager(activity)
        adapter = GifAdapter()

        binding.rv.adapter = adapter.apply {
            viewModel.gifsLiveData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.Success -> {
                        binding.pageProgressBar.visibility = View.INVISIBLE
                        response.data?.let {
                            adapter.differ.submitList(it.data)
                        }
                    }
                    is Resource.Error -> {
                        binding.pageProgressBar.visibility = View.INVISIBLE
                        response.data?.let {
                            Log.e("checkData", "MainFragment: error: ${it}")
                        }
                    }
                    is Resource.Loading -> {
                        binding.pageProgressBar.visibility = View.VISIBLE
                    }
                }
            }
        }

        binding.rv.adapter = adapter.apply {
            viewModel.searchedGifsLiveData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.Success -> {
                        response.data?.let {
                            binding.pageProgressBar.visibility = View.INVISIBLE
                            adapter.differ.submitList(it.data)
                        }
                    }
                    is Resource.Error -> {
                        binding.pageProgressBar.visibility = View.INVISIBLE
                        response.data?.let {
                            Log.e("checkData", "MainFragment: error: ${it}")
                        }
                    }
                    is Resource.Loading -> {
                        binding.pageProgressBar.visibility = View.VISIBLE
                    }
                }
            }
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

        adapter.setOnItemClickListener{
            val bundle = bundleOf("data" to it)
            findNavController().navigate(
                R.id.action_mainFragment_to_detailsFragment,
                bundle
            )
        }
    }


}
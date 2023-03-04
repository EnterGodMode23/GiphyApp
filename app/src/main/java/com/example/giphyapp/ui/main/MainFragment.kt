package com.example.giphyapp.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.giphyapp.R
import com.example.giphyapp.data.adapters.GifAdapter
import com.example.giphyapp.databinding.FragmentMainBinding
import com.example.giphyapp.domain.models.Data
import com.example.giphyapp.utils.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: GifAdapter
    private lateinit var searchField: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rv.layoutManager = LinearLayoutManager(activity)
        viewModel = MainViewModel()
        adapter = GifAdapter()


        binding.rv.adapter = adapter.apply {
            viewModel.gifsLiveData.observe(viewLifecycleOwner, Observer {
                this.differ.submitList(it)
            })
        }
        binding.rv.adapter = adapter.apply {
            viewModel.searchedGifsLiveData.observe(viewLifecycleOwner, Observer {
                this.differ.submitList(it)
            })
        }

        searchField = binding.edSearch
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
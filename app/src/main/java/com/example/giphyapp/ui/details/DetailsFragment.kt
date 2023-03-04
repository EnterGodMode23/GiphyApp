package com.example.giphyapp.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.giphyapp.R
import com.example.giphyapp.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val bundleArgs: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataArg = bundleArgs.data

        val url : String? = dataArg.images?.original?.url
        val user : String = dataArg.user?.username ?: "Unknown user"
        val title : String = dataArg.title ?: "Unknown title"
        val date : String = dataArg.import_datetime ?: "Unknown date"

        if (!url.isNullOrEmpty()) {
            dataArg.let {
                Glide.with(view)
                    .load(it.images?.original?.url)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .error(R.drawable.error_icon)
                    .into(binding.detailsGif)
            }
        }

        binding.detailsUser.text = user
        binding.detailsTitle.text = title
        binding.detailsDate.text = date
    }

}
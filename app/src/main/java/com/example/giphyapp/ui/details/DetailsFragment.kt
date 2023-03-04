package com.example.giphyapp.ui.details

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.giphyapp.R
import com.example.giphyapp.databinding.FragmentDetailsBinding
import com.example.giphyapp.ui.main.MainFragmentDirections

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val bundleArgs: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataArg = bundleArgs.data

        val url : String? = dataArg.images?.original?.url
        val user : String = dataArg.user?.username ?: "Unknown user"
        val title : String = dataArg.title ?: "Unknown title"
        val date : String = dataArg.import_datetime ?: "Unknown date"

        val circularProgressDrawable = CircularProgressDrawable(activity as Context)
        circularProgressDrawable.setColorSchemeColors(ContextCompat.getColor(
            activity as Context,
            R.color.teal_200))
        circularProgressDrawable.strokeWidth = 10f
        circularProgressDrawable.centerRadius = 40f
        circularProgressDrawable.start()

        if (!url.isNullOrEmpty()) {
            dataArg.let {
                Glide.with(view)
                    .load(it.images?.original?.url)
                    .placeholder(circularProgressDrawable)
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
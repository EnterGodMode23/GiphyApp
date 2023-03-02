package com.example.giphyapp.data.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.giphyapp.R
import com.example.giphyapp.databinding.ItemGifBinding
import com.example.giphyapp.domain.models.Data

class GifAdapter : RecyclerView.Adapter<GifAdapter.GifViewHolder>() {

    var gifs: List<Data> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    class GifViewHolder(val binding: ItemGifBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGifBinding.inflate(inflater, parent, false)

        return GifViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        val gif = gifs[position]
        val context = holder.itemView.context

        with(holder.binding) {

            val userName = gif.user?.username
            if (userName != null && userName != "") {
                user.text = userName
            } else {
                user.text = "Unknown user"
            }

            val desc = gif.user?.description
            if (desc != null && desc != "") {
                description.text = desc
            } else {
                description.text = "Description is empty"
            }

            Glide.with(context).load(gif.images.original.url).error(R.drawable.error_icon)
                .into(gifView)
        }
    }

    override fun getItemCount(): Int {
        return gifs.size
    }
}
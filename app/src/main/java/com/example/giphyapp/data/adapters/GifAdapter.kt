package com.example.giphyapp.data.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.giphyapp.R
import com.example.giphyapp.databinding.ItemGifBinding
import com.example.giphyapp.domain.models.Data

class GifAdapter : RecyclerView.Adapter<GifAdapter.GifViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, callback)

    class GifViewHolder(val binding: ItemGifBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGifBinding.inflate(inflater, parent, false)

        return GifViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        val gif = differ.currentList[position]
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

            Glide.with(context)
                .load(gif.images?.original?.url)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(R.drawable.error_icon)
                .into(gifView)
        }

        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(gif) }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Data) -> Unit)? = null

    fun setOnItemClickListener(listener: (Data) -> Unit){
        onItemClickListener = listener
    }
}
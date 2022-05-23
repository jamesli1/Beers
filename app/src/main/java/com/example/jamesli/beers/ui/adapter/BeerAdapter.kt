package com.example.jamesli.beers.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jamesli.beers.data.models.Beer
import com.example.jamesli.beers.databinding.ListItemBinding

class BeerAdapter : RecyclerView.Adapter<BeerAdapter.ListItemViewHolder>() {

    private lateinit var binding: ListItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        binding = ListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListItemViewHolder(binding)
    }

    class ListItemViewHolder(itemBinding: ListItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Beer>() {
        override fun areItemsTheSame(oldItem: Beer, newItem: Beer): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Beer, newItem: Beer): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        val beer = differ.currentList[position]
        holder.itemView.apply {
            binding.itemTitle.text = beer.name
            binding.itemDescription.text = beer.tagline
            binding.let {
                binding.itemImage.let { image ->
                    Glide.with(it.root)
                        .load(beer.imageUrl)
                        .centerInside()
                        .placeholder(null)
                        .into(image)
                }
            }

            setOnClickListener { onItemClickListener?.let { it(beer) }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    private var onItemClickListener: ((Beer) -> Unit)? = null

    fun setOnItemClickListener(listener: (Beer) -> Unit) {
        onItemClickListener = listener
    }
}
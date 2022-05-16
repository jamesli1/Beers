package com.example.jamesli.beers.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jamesli.beers.R
import com.example.jamesli.beers.data.models.Beer
import com.example.jamesli.beers.databinding.ListItemBinding

class BeersPagingAdapter :
    PagingDataAdapter<Beer, BeersPagingAdapter.ViewHolder>(BeerDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(beer: Beer?) {
            binding.apply {
                itemTitle.text = beer?.name
                itemDescription.text = beer?.tagline
                itemImage.let { image ->
                    Glide.with(root)
                        .load(beer?.imageUrl)
                        .centerInside()
                        .into(image)
                }
                executePendingBindings()
            }

            itemView.setOnClickListener {
                val bundle = bundleOf("id" to beer?.id)
                findNavController(it).navigate(
                    R.id.action_list_fragment_to_detail_fragment,
                    bundle
                )
            }
        }
    }
}

object BeerDiffCallback : DiffUtil.ItemCallback<Beer>() {
    override fun areItemsTheSame(oldItem: Beer, newItem: Beer): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Beer, newItem: Beer): Boolean {
        return oldItem == newItem
    }
}
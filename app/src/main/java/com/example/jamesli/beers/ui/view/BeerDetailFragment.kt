package com.example.jamesli.beers.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.jamesli.beers.databinding.BeerDetailFragmentBinding
import com.example.jamesli.beers.ui.viewmodel.BeerDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BeerDetailFragment : Fragment() {

    private val args: BeerDetailFragmentArgs by navArgs()
    private val beerDetailViewModel: BeerDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = BeerDetailFragmentBinding.inflate(
            inflater,
            container,
            false
        ).apply {
            viewModel = beerDetailViewModel
            lifecycleOwner = viewLifecycleOwner
            beerDetailViewModel.getBeerById(args.id)
        }
        updateData(binding)
        return binding.root
    }

    private fun updateData(binding: BeerDetailFragmentBinding) {
        beerDetailViewModel.beerDetail.observe(viewLifecycleOwner, { beer ->
            binding.tvName.text = beer.name
            binding.tvAbv.text = beer.abv
            binding.tvIbu.text = beer.ibu
            binding.tvFirstBrewed.text = beer.firstBrewed
            binding.tvDescription.text = beer.description
            binding.let { Glide.with(it.root).load(beer.imageUrl).into(binding.ivImage) }
        })
    }
}
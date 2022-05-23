package com.example.jamesli.beers.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jamesli.beers.databinding.BeerListFragmentBinding
import com.example.jamesli.beers.ui.adapter.BeersPagingAdapter
import com.example.jamesli.beers.ui.viewmodel.BeerListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BeerListFragment : Fragment() {

    private val beerListViewModel: BeerListViewModel by viewModels()
    private var beersPagingAdapter = BeersPagingAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = BeerListFragmentBinding.inflate(
            inflater,
            container,
            false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
            setRecyclerView(this)
        }
        return binding.root
    }

    private fun setRecyclerView(binding: BeerListFragmentBinding) {
        binding.beerList.apply {
            setHasFixedSize(true)
            adapter = beersPagingAdapter
            layoutManager = LinearLayoutManager(requireContext())
            updateData()
        }
    }

    private fun updateData() {
        lifecycleScope.launch {
            beerListViewModel.beers.collectLatest {
                beersPagingAdapter.submitData(it)
            }
        }
    }
}
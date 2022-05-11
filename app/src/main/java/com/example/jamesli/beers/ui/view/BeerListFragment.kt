package com.example.jamesli.beers.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jamesli.beers.R
import com.example.jamesli.beers.databinding.BeerListFragmentBinding
import com.example.jamesli.beers.ui.adapter.BeerAdapter
import com.example.jamesli.beers.ui.viewmodel.BeerListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BeerListFragment : Fragment() {

    private val beerListViewModel: BeerListViewModel by viewModels()
    private var beerAdapter: BeerAdapter? = null
    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = BeerListFragmentBinding.inflate(
            inflater,
            container,
            false
        ).apply {
            viewModel = beerListViewModel
            lifecycleOwner = viewLifecycleOwner
            beerListViewModel.getBeers(beerListViewModel.page)
        }
        setRecyclerView(binding)
        return binding.root
    }

    private fun setRecyclerView(binding: BeerListFragmentBinding) {
        beerAdapter = BeerAdapter()
        binding.beerList.apply {
            setHasFixedSize(true)
            adapter = beerAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addOnScrollListener(this@BeerListFragment.scrollListener)
        }
        beerAdapter?.setOnItemClickListener {
            val bundle = bundleOf("id" to it.id)
            findNavController().navigate(
                R.id.action_list_fragment_to_detail_fragment,
                bundle
            )
        }
        updateData()
    }

    private fun updateData() {
        beerListViewModel.beers.observe(viewLifecycleOwner, {
            beerAdapter?.differ?.submitList(it)
            })
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= 25
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning
                    && isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                beerListViewModel.nextPage()
                isScrolling = false
            }
        }
    }
}
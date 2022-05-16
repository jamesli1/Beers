package com.example.jamesli.beers.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.jamesli.beers.data.models.Beer
import com.example.jamesli.beers.domain.usecase.GetBeersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class BeerListViewModel @Inject constructor(
    getBeersUseCase: GetBeersUseCase,
) : ViewModel() {
    val beers: Flow<PagingData<Beer>> =
        getBeersUseCase().flow.cachedIn(viewModelScope)
}
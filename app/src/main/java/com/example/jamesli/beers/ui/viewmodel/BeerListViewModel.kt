package com.example.jamesli.beers.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jamesli.beers.data.models.Beer
import com.example.jamesli.beers.domain.usecase.GetBeersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class BeerListViewModel @Inject constructor(
    private val getBeersUseCase: GetBeersUseCase
) : ViewModel() {

    var compositeDisposable = CompositeDisposable()
    var page = 1

    private val _beers = MutableLiveData<List<Beer>>()
    val beers: LiveData<List<Beer>> = _beers

    fun getBeers(page: Int) {
        val disposable = getBeersUseCase(page)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { beers ->
                _beers.value = _beers.value.orEmpty().plus(beers)
            }
        compositeDisposable.add(disposable)
    }

    fun nextPage() {
        page += 1
        getBeers(page)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
package com.example.jamesli.beers.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jamesli.beers.data.models.Beer
import com.example.jamesli.beers.domain.usecase.GetBeerByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class BeerDetailViewModel @Inject constructor(
    private val getBeerByIdUseCase: GetBeerByIdUseCase
) : ViewModel() {

    private val _beerDetail = MutableLiveData<Beer>()
    val beerDetail: LiveData<Beer> = _beerDetail
    var compositeDisposable = CompositeDisposable()

    fun getBeerById(id: Int) {
        val disposable = getBeerByIdUseCase(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { it -> _beerDetail.value = it }
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}

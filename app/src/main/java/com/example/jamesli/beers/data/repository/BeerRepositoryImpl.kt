package com.example.jamesli.beers.data.repository

import com.example.jamesli.beers.data.api.ApiService
import com.example.jamesli.beers.data.models.Beer
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BeerRepositoryImpl @Inject constructor(private val apiService: ApiService) : BeerRepository {

    override fun getBeers(page: Int): Single<List<Beer>> {
        return apiService.getBeers(page, 25)
            .subscribeOn(Schedulers.io())
    }

    override fun getBeerById(id: Int): Single<List<Beer>> {
        return apiService.getBeerById(id = id)
            .subscribeOn(Schedulers.io())
    }
}
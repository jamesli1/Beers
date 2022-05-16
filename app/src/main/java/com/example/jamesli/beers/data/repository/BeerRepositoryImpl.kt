package com.example.jamesli.beers.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.jamesli.beers.data.api.ApiService
import com.example.jamesli.beers.data.models.Beer
import com.example.jamesli.beers.util.LIMIT
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BeerRepositoryImpl @Inject constructor(private val apiService: ApiService) : BeerRepository {

    override fun getBeers(): Pager<Int, Beer> {
        return Pager(PagingConfig(pageSize = LIMIT)) {
            BeerPagingDataSource(apiService)
        }
    }

    override fun getBeerById(id: Int): Single<List<Beer>> {
        return apiService.getBeerById(id)
            .subscribeOn(Schedulers.io())
    }
}
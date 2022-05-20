package com.example.jamesli.beers.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.jamesli.beers.data.api.ApiService
import com.example.jamesli.beers.data.db.BeerDatabase
import com.example.jamesli.beers.data.models.Beer
import com.example.jamesli.beers.util.LIMIT
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@ExperimentalPagingApi
class BeerRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val beerDatabase: BeerDatabase,
    private val beerRemoteMediator: BeerRemoteMediator
) : BeerRepository {

    override fun getBeers(): Pager<Int, Beer> {
        return Pager(
            config = PagingConfig(pageSize = LIMIT),
            remoteMediator = beerRemoteMediator
        ) {
            /*get items from database*/
            beerDatabase.beerDao().getAll()

            /*get items from network*/
            //BeerPagingDataSource(apiService)
        }
    }

    override fun getBeerById(id: Int): Single<List<Beer>> {
        return apiService.getBeerById(id)
            .subscribeOn(Schedulers.io())
    }
}
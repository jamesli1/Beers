package com.example.jamesli.beers.data.repository

import androidx.paging.Pager
import com.example.jamesli.beers.data.models.Beer
import io.reactivex.Single

interface BeerRepository {

    fun getBeers(): Pager<Int, Beer>

    fun getBeerById(id: Int): Single<List<Beer>>

}
package com.example.jamesli.beers.data.repository

import com.example.jamesli.beers.data.models.Beer
import io.reactivex.Single

interface BeerRepository {

    fun getBeers(page: Int): Single<List<Beer>>

    fun getBeerById(id: Int):  Single<List<Beer>>

}
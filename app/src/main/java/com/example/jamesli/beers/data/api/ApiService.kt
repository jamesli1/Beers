package com.example.jamesli.beers.data.api

import com.example.jamesli.beers.data.models.Beer
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

//    https://api.punkapi.com/v2/beers
    @GET("beers")
    suspend fun getBeers(
        @Query("page") pageNumber: Int,
        @Query("per_page") perPage: Int
    ): List<Beer>

//    https://api.punkapi.com/v2/beers/1
    @GET("beers/{id}")
    fun getBeerById(
        @Path("id") id: Int
    ): Single<List<Beer>>
}
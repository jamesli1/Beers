package com.example.jamesli.beers.data.repository

import androidx.paging.ExperimentalPagingApi
import com.example.jamesli.beers.data.api.ApiService
import com.example.jamesli.beers.data.db.BeerDatabase
import com.example.jamesli.beers.data.models.Beer
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class BeerRepositoryImplTest {
    private val apiService: ApiService = mockk()
    private val beer: Beer = mockk()
    private val beerDatabase: BeerDatabase = mockk()

    @ExperimentalPagingApi
    private val beerRemoteMediator: BeerRemoteMediator = mockk()
    private val id = 1

    @ExperimentalPagingApi
    private lateinit var subject: BeerRepositoryImpl

    @ExperimentalPagingApi
    @Before
    fun setUp() {
        every { apiService.getBeerById(any()) }.returns(Single.just(listOf(beer)))

        subject = BeerRepositoryImpl(apiService, beerDatabase, beerRemoteMediator)
    }

    @ExperimentalPagingApi
    @Test
    fun getBeerById_callServiceGetBeerById() {
        subject.getBeerById(id)

        verify { apiService.getBeerById(id) }
    }
}
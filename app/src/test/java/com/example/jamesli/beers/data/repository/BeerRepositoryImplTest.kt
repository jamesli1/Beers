package com.example.jamesli.beers.data.repository

import com.example.jamesli.beers.data.api.ApiService
import com.example.jamesli.beers.data.models.Beer
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class BeerRepositoryImplTest{
    private val apiService: ApiService = mockk()
    private val beer : Beer = mockk()
    private val page = 1
    private val perPage = 25
    private val id = 2

    private lateinit var subject: BeerRepositoryImpl

    @Before
    fun setUp() {
        every { apiService.getBeers(any(),any()) }.returns(Single.just(listOf(beer)))
        every { apiService.getBeerById(any()) }.returns(Single.just(listOf(beer)))

        subject = BeerRepositoryImpl(apiService)
    }

    @Test
    fun getBeers_callServiceGerBeers() {
        subject.getBeers(page)

        verify { apiService.getBeers(page, perPage) }
    }

    @Test
    fun getBeerById_callServiceGetBeerById() {
        subject.getBeerById(id)

        verify { apiService.getBeerById(id) }
    }
}
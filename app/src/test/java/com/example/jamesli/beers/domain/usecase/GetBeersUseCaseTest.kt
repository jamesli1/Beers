package com.example.jamesli.beers.domain.usecase

import com.example.jamesli.beers.data.models.Beer
import com.example.jamesli.beers.data.repository.BeerRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class GetBeersUseCaseTest {
    private val repository: BeerRepository = mockk()
    private val beer: Beer = mockk()
    private val page = 1

    private lateinit var subject: GetBeersUseCase

    @Before
    fun setUp() {
        every { repository.getBeers(any()) }.returns(Single.just(listOf(beer)))

        subject = GetBeersUseCase(repository)
    }

    @Test
    fun invoke_callGetBeer() {
        subject.invoke(page)

        verify { repository.getBeers(page) }
    }
}
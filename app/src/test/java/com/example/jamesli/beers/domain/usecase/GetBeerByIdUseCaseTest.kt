package com.example.jamesli.beers.domain.usecase

import com.example.jamesli.beers.data.models.Beer
import com.example.jamesli.beers.data.repository.BeerRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class GetBeerByIdUseCaseTest {
    private val repository: BeerRepository = mockk()
    private val beer: Beer = mockk()
    private val id = 1

    private lateinit var subject: GetBeerByIdUseCase

    @Before
    fun setUp() {
        every { repository.getBeerById(any()) }.returns(Single.just(listOf(beer)))

        subject = GetBeerByIdUseCase(repository)
    }

    @Test
    fun invoke_callGetBeerById() {
        subject.invoke(id)

        verify { repository.getBeerById(id) }
    }
}
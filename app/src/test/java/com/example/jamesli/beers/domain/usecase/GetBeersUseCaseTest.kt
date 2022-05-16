package com.example.jamesli.beers.domain.usecase

import androidx.paging.Pager
import com.example.jamesli.beers.data.models.Beer
import com.example.jamesli.beers.data.repository.BeerRepository
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class GetBeersUseCaseTest {
    private val repository: BeerRepository = mockk()
    private val pager: Pager<Int, Beer> = mockk()

    private lateinit var subject: GetBeersUseCase

    @Before
    fun setUp() {
        coEvery { repository.getBeers() }.returns(pager)

        subject = GetBeersUseCase(repository)
    }

    @Test
    fun invoke_callGetBeer() {
        subject.invoke()

        verify { repository.getBeers() }
    }
}
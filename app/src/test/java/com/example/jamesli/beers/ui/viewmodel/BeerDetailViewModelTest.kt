package com.example.jamesli.beers.ui.viewmodel

import com.example.jamesli.beers.data.models.Beer
import com.example.jamesli.beers.domain.usecase.GetBeerByIdUseCase
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class BeerDetailViewModelTest {
    private val getBeerByIdUseCase: GetBeerByIdUseCase = mockk()
    private val beer: Beer = mockk()
    private val id = 1

    private lateinit var subject: BeerDetailViewModel

    @Before
    fun setUp() {
        subject = BeerDetailViewModel(getBeerByIdUseCase)
    }

    @Test
    fun getBeerById_noErrorsAndComplete() {
        every { getBeerByIdUseCase.invoke(id) } returns Single.just(beer)

        val testObserver = getBeerByIdUseCase(id).test()

        testObserver.assertNoErrors()
            .assertComplete()
    }
}
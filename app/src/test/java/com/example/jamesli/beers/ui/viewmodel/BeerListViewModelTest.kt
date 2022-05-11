package com.example.jamesli.beers.ui.viewmodel

import com.example.jamesli.beers.data.models.Beer
import com.example.jamesli.beers.domain.usecase.GetBeersUseCase
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class BeerListViewModelTest {
    private val getBeersUseCase: GetBeersUseCase = mockk()
    private val beer: Beer = mockk()
    private val page = 1

    private lateinit var subject: BeerListViewModel

    @Before
    fun setUp() {
        subject = BeerListViewModel(getBeersUseCase)
    }

    @Test
    fun getBeers_noErrorAndCompletes() {
        every { getBeersUseCase.invoke(page)}.returns(Single.just(listOf(beer)))

        val testObserver = getBeersUseCase(page).test()

        testObserver.assertNoErrors()
            .assertComplete()
    }
}
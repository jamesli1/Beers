package com.example.jamesli.beers.ui.viewmodel

import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.jamesli.beers.data.models.Beer
import com.example.jamesli.beers.domain.usecase.GetBeersUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

class BeerListViewModelTest {
    private val getBeersUseCase: GetBeersUseCase = mockk()
    private val pager: Pager<Int, Beer> = mockk()
    private val flow: Flow<PagingData<Beer>> = mockk()
    private val testDispatcher: TestDispatcher = StandardTestDispatcher()

    private lateinit var subject: BeerListViewModel

    @Before
    fun setUp() {

        every { getBeersUseCase.invoke() }.returns(pager)
        every { pager.flow }.returns(flow)
        Dispatchers.setMain(testDispatcher)
        subject = BeerListViewModel(getBeersUseCase)
    }

    @Test
    fun verifyGetBeersUseCase() {
        verify { getBeersUseCase() }
    }
}
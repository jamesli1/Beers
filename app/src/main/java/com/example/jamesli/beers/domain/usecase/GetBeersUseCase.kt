package com.example.jamesli.beers.domain.usecase

import androidx.paging.Pager
import com.example.jamesli.beers.data.models.Beer
import com.example.jamesli.beers.data.repository.BeerRepository
import javax.inject.Inject

class GetBeersUseCase @Inject constructor(private val repository: BeerRepository) {
    operator fun invoke(): Pager<Int, Beer> = repository.getBeers()
}
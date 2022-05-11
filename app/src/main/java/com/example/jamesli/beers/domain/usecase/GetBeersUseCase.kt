package com.example.jamesli.beers.domain.usecase

import com.example.jamesli.beers.data.models.Beer
import com.example.jamesli.beers.data.repository.BeerRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetBeersUseCase @Inject constructor(private val repository: BeerRepository) {
    operator fun invoke(page: Int): Single<List<Beer>> {
        return repository.getBeers(page = page)
            .subscribeOn(Schedulers.io())
    }
}
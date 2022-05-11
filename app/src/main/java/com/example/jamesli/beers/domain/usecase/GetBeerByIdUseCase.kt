package com.example.jamesli.beers.domain.usecase

import com.example.jamesli.beers.data.models.Beer
import com.example.jamesli.beers.data.repository.BeerRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetBeerByIdUseCase @Inject constructor(private val repository: BeerRepository) {
    operator fun invoke(id: Int): Single<Beer> {
        return repository.getBeerById(id = id)
            .map { it.first()}
            .subscribeOn(Schedulers.io())
    }
}
package com.example.jamesli.beers.di

import androidx.paging.ExperimentalPagingApi
import com.example.jamesli.beers.data.api.ApiService
import com.example.jamesli.beers.data.db.BeerDatabase
import com.example.jamesli.beers.data.repository.BeerRemoteMediator
import com.example.jamesli.beers.data.repository.BeerRepository
import com.example.jamesli.beers.data.repository.BeerRepositoryImpl
import com.example.jamesli.beers.util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Singleton
    @Provides
    fun provideRxJava2CallAdapterFactory(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .baseUrl(BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @ExperimentalPagingApi
    @Singleton
    @Provides
    fun provideBeerRepository(api: ApiService, beerDatabase: BeerDatabase, beerRemoteMediator: BeerRemoteMediator): BeerRepository {
        return BeerRepositoryImpl(api, beerDatabase, beerRemoteMediator)
    }
}
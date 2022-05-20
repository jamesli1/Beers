package com.example.jamesli.beers.di

import android.app.Application
import androidx.room.Room
import com.example.jamesli.beers.data.db.BeerDao
import com.example.jamesli.beers.data.db.BeerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    internal fun provideAppDatabase(application: Application): BeerDatabase {
        return Room.databaseBuilder(
            application,
            BeerDatabase::class.java,
            "BeerDB.db"
        ).allowMainThreadQueries().build()
    }

    @Provides
    fun provideBeerDao(beerDatabase: BeerDatabase): BeerDao {
        return beerDatabase.beerDao()
    }
}
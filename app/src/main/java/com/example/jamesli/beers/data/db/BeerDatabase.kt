package com.example.jamesli.beers.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jamesli.beers.data.models.Beer
import com.example.jamesli.beers.data.models.BeerRemoteKeys

@Database(entities = [Beer::class, BeerRemoteKeys::class], version = 1)
abstract class BeerDatabase : RoomDatabase() {
    abstract fun beerDao(): BeerDao
    abstract fun beerRemoteKeysDao(): BeerRemoteKeysDao
}
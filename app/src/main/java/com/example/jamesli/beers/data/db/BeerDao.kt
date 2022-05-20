package com.example.jamesli.beers.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jamesli.beers.data.models.Beer

@Dao
interface BeerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(beer: List<Beer>)

    @Query("SELECT * from beers ORDER BY id ASC")
    fun getAll(): PagingSource<Int, Beer>

    @Query("DELETE FROM beers")
    suspend fun clearAll()
}
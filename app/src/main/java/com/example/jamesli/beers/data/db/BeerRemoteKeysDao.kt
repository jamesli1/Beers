package com.example.jamesli.beers.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jamesli.beers.data.models.BeerRemoteKeys

@Dao
interface BeerRemoteKeysDao {

    @Query("SELECT * FROM remote_keys WHERE id = :id")
    suspend fun getRemoteKeys(id: Int): BeerRemoteKeys?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys : List<BeerRemoteKeys>)

    @Query("DELETE FROM remote_keys")
    suspend fun deleteAllRemoteKeys()
}
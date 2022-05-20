package com.example.jamesli.beers.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.jamesli.beers.data.api.ApiService
import com.example.jamesli.beers.data.db.BeerDatabase
import com.example.jamesli.beers.data.models.Beer
import com.example.jamesli.beers.data.models.BeerRemoteKeys
import com.example.jamesli.beers.util.LIMIT
import javax.inject.Inject

@ExperimentalPagingApi
class BeerRemoteMediator @Inject constructor(
    private val apiService: ApiService,
    private val database: BeerDatabase
) : RemoteMediator<Int, Beer>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, Beer>): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }
            val response = apiService.getBeers(page, LIMIT)
            var endOfPaginationReached = false
            if (response.isNotEmpty()) {
                    database.withTransaction {
                        if (loadType == LoadType.REFRESH) {
                            database.beerDao().clearAll()
                            database.beerRemoteKeysDao().deleteAllRemoteKeys()
                        }
                        val prevPage = if (page == 1) null else page - 1
                        val nextPage = page + 1

                        val keys = response.map { beer ->
                            BeerRemoteKeys(
                                id = beer.id,
                                prevPage = prevPage,
                                nextPage = nextPage
                            )
                        }
                        database.beerRemoteKeysDao().addAllRemoteKeys(remoteKeys = keys)
                        database.beerDao().insertAll(beer = response)
                    }
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Beer>,
    ): BeerRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.beerRemoteKeysDao().getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Beer>,
    ): BeerRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { it ->
                database.beerRemoteKeysDao().getRemoteKeys(id = it.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Beer>,
    ): BeerRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { it ->
                database.beerRemoteKeysDao().getRemoteKeys(id= it.id)
            }
    }
}
package com.example.jamesli.beers.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.jamesli.beers.data.api.ApiService
import com.example.jamesli.beers.data.models.Beer
import com.example.jamesli.beers.util.LIMIT
import javax.inject.Inject

class BeerPagingDataSource @Inject constructor(private val apiService: ApiService) :
    PagingSource<Int, Beer>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Beer> {
        return try {
            val page = params.key ?: 1
            val response = apiService.getBeers(page, LIMIT)
            LoadResult.Page(
                data = response,
                prevKey = if (page == 1) null else page - 1,
                nextKey = page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Beer>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(
                anchorPosition
            )?.prevKey
        }
    }
}
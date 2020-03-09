package com.andrew.newsapp.domain

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.PagedList.BoundaryCallback
import com.andrew.newsapp.domain.newsDatabase.TopStoriesDao
import com.andrew.newsapp.domain.newsDatabase.dbInstance
import com.andrew.newsapp.entities.DbNewsPiece
import timber.log.Timber
import java.lang.Exception

interface TopStoriesRepository {
    suspend fun refreshNews(type: String): Int
    fun retrieveNews(
        callback: BoundaryCallback<DbNewsPiece>,
        pageSize: Int
    ): LiveData<PagedList<DbNewsPiece>>
}

val topStoriesRepository by lazy { DefaultTopStoriesRepository() }

class DefaultTopStoriesRepository(
    private val localDataSource: TopStoriesDao = dbInstance.topStoriesDao,
    private val remoteDataSource: NewsApi = newsApi
) : TopStoriesRepository {
    override suspend fun refreshNews(type: String) = remoteDataSource
        .also { if (type == ARTS) localDataSource.deleteAll() }
        .run {
            try {
                val response = getTopStoriesAsync(type).await()
                Timber.i("response code: ${response.code()}")
                if (response.isSuccessful) localDataSource.insertNews(response.body()?.news?.toDpNews())
                response.code()
            } catch (exception: Exception) {
                exception.printStackTrace()
                0
            }
        }

    override fun retrieveNews(
        callback: BoundaryCallback<DbNewsPiece>,
        pageSize: Int
    ) = LivePagedListBuilder(localDataSource.queryAll(), pageSize)
        .setBoundaryCallback(callback)
        .build()
}

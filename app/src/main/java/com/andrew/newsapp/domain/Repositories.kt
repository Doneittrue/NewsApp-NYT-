package com.andrew.newsapp.domain

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.PagedList.BoundaryCallback
import com.andrew.newsapp.domain.newsDatabase.TopStoriesDao
import com.andrew.newsapp.domain.newsDatabase.dbInstance
import com.andrew.newsapp.entities.DbNewsPiece
import com.andrew.newsapp.entities.NewsResponse
import retrofit2.Response
import java.lang.Exception

interface TopStoriesRepository {
    suspend fun refreshNews(type: String): String?
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
    override suspend fun refreshNews(type: String) = remoteDataSource.run {
        try {
            val response = getTopStoriesAsync(type).await()
            handleDbOperations(response, type == types[0])
            response.message() ?: ""
        } catch (exception: Exception) {
            exception.printStackTrace()
            exception.message ?:"Error while loading"
        }
    }

    private fun handleDbOperations(
        response: Response<NewsResponse>,
        isTypeMatching: Boolean
    ) = if (response.isSuccessful) {
        if (isTypeMatching) localDataSource.deleteAll()
        localDataSource.insertNews(response.body()?.news?.toDpNews())
    } else Unit

    override fun retrieveNews(
        callback: BoundaryCallback<DbNewsPiece>,
        pageSize: Int
    ) = LivePagedListBuilder(localDataSource.queryAll(), pageSize)
        .setBoundaryCallback(callback)
        .build()
}

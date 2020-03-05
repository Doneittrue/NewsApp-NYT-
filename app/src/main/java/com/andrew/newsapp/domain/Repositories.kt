package com.andrew.newsapp.domain

import androidx.lifecycle.LiveData
import com.andrew.newsapp.domain.newsDatabase.TopStoriesDao
import com.andrew.newsapp.domain.newsDatabase.dbInstance
import com.andrew.newsapp.entities.DbNewsPiece
import java.lang.Exception

interface TopStoriesRepository {
    suspend fun refreshNews(type: String): Int
     fun retrieveNews(): LiveData<List<DbNewsPiece>>
}

 val topStoriesRepository by lazy { DefaultTopStoriesRepository() }

class DefaultTopStoriesRepository(
    private val localDataSource: TopStoriesDao= dbInstance.topStoriesDao,
    private val remoteDataSource: NewsApi = newsApi
) : TopStoriesRepository {
    override suspend fun refreshNews(type: String)= remoteDataSource
        .also { if (type == ARTS) localDataSource.deleteAll() }
        .run {
            try {
                val response =getTopStoriesAsync(type).await()
                if (response.isSuccessful) localDataSource.insertNews(response.body()?.news?.toDpNews())
                response.code()
            }catch (exception:Exception){
                println(exception.message)
                0
            }
        }

    override fun retrieveNews()= localDataSource.queryAll()
}



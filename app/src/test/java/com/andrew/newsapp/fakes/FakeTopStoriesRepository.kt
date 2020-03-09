package com.andrew.newsapp.fakes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.andrew.newsapp.domain.TopStoriesRepository
import com.andrew.newsapp.entities.DbNewsPiece

class FakeTopStoriesRepository(
    var responseCode: Int = 0,
    var data: List<DbNewsPiece> = emptyList()
) : TopStoriesRepository {
    override suspend fun refreshNews(type: String) = responseCode
    override fun retrieveNews(
        callback: PagedList.BoundaryCallback<DbNewsPiece>,
        pageSize: Int
    )=MutableLiveData<PagedList<DbNewsPiece>>()


}
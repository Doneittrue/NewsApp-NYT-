package com.andrew.newsapp.fakes

import androidx.lifecycle.MutableLiveData
import com.andrew.newsapp.domain.TopStoriesRepository
import com.andrew.newsapp.entities.DbNewsPiece

class FakeTopStoriesRepository(
    var responseCode: Int = 0,
    var data: List<DbNewsPiece> = emptyList()
) : TopStoriesRepository {
    override suspend fun refreshNews(type: String) = responseCode
    override fun retrieveNews() = MutableLiveData<List<DbNewsPiece>>()
        .apply { value = data }
}
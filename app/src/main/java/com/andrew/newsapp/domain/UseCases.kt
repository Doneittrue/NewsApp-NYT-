package com.andrew.newsapp.domain

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.andrew.newsapp.entities.DbNewsPiece

sealed class TopStoriesState
object Idle : TopStoriesState()
object Loading : TopStoriesState()
object Success : TopStoriesState()
class Error(val message: String) : TopStoriesState()
class Empty(val message: String) : TopStoriesState()

class RefreshTopStoriesUseCase(private val repository: TopStoriesRepository = topStoriesRepository) {
    suspend operator fun invoke(
        isConnected: Boolean,
        type: String,
        state: MutableLiveData<TopStoriesState>
    ) = repository
        .takeIf { isConnected }
        ?.takeUnless { state.value ?: state.postValue(Idle) is Loading }
        ?.also { state.postValue(Loading) }
        ?.run { refreshNews(type) }
        ?.let { state.postValue(if (it == "OK") Success else Error(it)) }

    private fun onNotConnected(
        isConnected: Boolean,
        state: MutableLiveData<TopStoriesState>
    ) = isConnected
        .also { if (!it) state.postValue(Error("No internet connection")) }
}

class GetTopStoriesUseCase(private val repository: TopStoriesRepository = topStoriesRepository) {
    operator fun invoke(
        callback: PagedList.BoundaryCallback<DbNewsPiece>,
        pageSize: Int
    ) = repository.retrieveNews(callback, pageSize)
}
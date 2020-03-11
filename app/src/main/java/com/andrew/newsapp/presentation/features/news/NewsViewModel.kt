package com.andrew.newsapp.presentation.features.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.andrew.newsapp.domain.*
import com.andrew.newsapp.entities.DbNewsPiece
import kotlinx.coroutines.*

class NewsViewModel(
    private val refreshTopStoriesUseCase: RefreshTopStoriesUseCase = RefreshTopStoriesUseCase(),
    private val getTopStories: GetTopStoriesUseCase = GetTopStoriesUseCase(),
    private val job:Job= Job()
) : ViewModel() {

    private val _state = MutableLiveData<TopStoriesState>()
    val state: LiveData<TopStoriesState> get() = _state

    var index = 0
        private set

    fun refreshTopStories(
        isConnected: Boolean,
        type: String
    ) = CoroutineScope(job+Dispatchers.IO).launch {
        refreshTopStoriesUseCase(isConnected, type, _state)
    }



    fun callAgain(
        isConnected: Boolean,
        type: String = types[index++]
    ) = type
        .takeIf { types.indexOf(it) < types.size }
        ?.let {refreshTopStories(isConnected, it) } ?: Unit

    fun retrieveTopStories(
        callback: PagedList.BoundaryCallback<DbNewsPiece>,
        size: Int
    ) = getTopStories(callback, size)

    fun onNonEmptyResult(notEmpty:Boolean){
        _state.value=if (notEmpty)Success else Error("No stories to show please check your connection")
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
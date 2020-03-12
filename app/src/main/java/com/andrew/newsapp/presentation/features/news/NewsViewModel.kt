package com.andrew.newsapp.presentation.features.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import com.andrew.newsapp.domain.*
import com.andrew.newsapp.entities.DbNewsPiece
import com.andrew.newsapp.domain.Error
import kotlinx.coroutines.*

class NewsViewModel(
    isConnected: Boolean,
    private val refreshTopStoriesUseCase: RefreshTopStoriesUseCase = RefreshTopStoriesUseCase(),
    private val getTopStories: GetTopStoriesUseCase = GetTopStoriesUseCase(),
    private val job: Job = Job()
) : ViewModel() {

    private val _state = MutableLiveData<TopStoriesState>()
    val state: LiveData<TopStoriesState> get() = _state

    var index = 0
        private set

    init {
        refreshTopStories(isConnected, types[index])
    }

    fun refreshTopStories(
        isConnected: Boolean,
        type: String
    ) = CoroutineScope(job + Dispatchers.IO).launch {
        refreshTopStoriesUseCase(isConnected, type, _state)
    }

    fun callAgain(
        isConnected: Boolean,
        lastType: String = types[index]
    ) = lastType
        .takeIf { types.indexOf(it) < types.size - 1 }
        ?.let { refreshTopStories(isConnected, types[index++]) } ?: Unit

    fun retrieveTopStories(
        callback: PagedList.BoundaryCallback<DbNewsPiece>,
        size: Int
    ) = getTopStories(callback, size)

    fun onNonEmptyResult(notEmpty: Boolean, isConnected: Boolean) {
        if (!notEmpty) _state.value = Empty(if (isConnected) "Error while loading" else "No internet connection")
        if (notEmpty && !isConnected) _state.value = Error("No internet connection")
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}


class NewsViewModelFactory(private val isConnected: Boolean) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(NewsViewModel::class.java)) NewsViewModel(isConnected) as T
        else throw IllegalArgumentException("modle class is not Identified")
    }
}
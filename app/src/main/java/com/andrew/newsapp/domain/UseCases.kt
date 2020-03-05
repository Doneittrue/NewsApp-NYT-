package com.andrew.newsapp.domain

import androidx.lifecycle.MutableLiveData

sealed class TopStoriesState
object Idle : TopStoriesState()
object Loading : TopStoriesState()
object Success : TopStoriesState()
class Error(val message: String) : TopStoriesState()

class RefreshTopStoriesUseCase(private val repository: TopStoriesRepository = topStoriesRepository) {
    suspend operator fun invoke(
        isConnected: Boolean,
        type: String,
        state: MutableLiveData<TopStoriesState>
    ) = repository
        .takeIf {
            if (!isConnected) state.value = Error("Check your internet connection");isConnected
        }
        ?.also { state.value = Loading }
        ?.run { refreshNews(type) }
        ?.let { state.value = if (it == 200) Success else Error("Error While Loading") }
}

fun getTopStories(repository: TopStoriesRepository = topStoriesRepository) =
    repository.retrieveNews()
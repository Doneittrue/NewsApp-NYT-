package com.andrew.newsapp.presentation.features.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andrew.newsapp.domain.RefreshTopStoriesUseCase
import com.andrew.newsapp.domain.TopStoriesState
import com.andrew.newsapp.domain.getTopStories
import com.andrew.newsapp.entities.DbNewsPiece
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class NewsViewModel(
    private val refreshTopStoriesUseCase: RefreshTopStoriesUseCase= RefreshTopStoriesUseCase(),
    private val topStories: LiveData<List<DbNewsPiece>> = getTopStories(),
private val mainScope:CoroutineScope=CoroutineScope(Dispatchers.Main)
):ViewModel(){

    private val _state=MutableLiveData<TopStoriesState>()
    val state :LiveData<TopStoriesState> get() = _state

    fun refreshTopStories(
        isConnected:Boolean,
        type:String
    )=mainScope.launch {
        refreshTopStoriesUseCase(isConnected,type,_state)
    }

    override fun onCleared() {
        super.onCleared()
        mainScope.cancel()
    }
}
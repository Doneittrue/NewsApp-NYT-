package com.andrew.newsapp.presentation.features.news

import androidx.paging.PagedList
import com.andrew.newsapp.entities.DbNewsPiece

class TopStoriesCallbacks(
private val   loadPage:()->Unit
):PagedList.BoundaryCallback<DbNewsPiece>() {
    override fun onZeroItemsLoaded() {
        loadPage()
    }

    override fun onItemAtEndLoaded(itemAtEnd: DbNewsPiece) {
        super.onItemAtEndLoaded(itemAtEnd)
        loadPage()
    }
}
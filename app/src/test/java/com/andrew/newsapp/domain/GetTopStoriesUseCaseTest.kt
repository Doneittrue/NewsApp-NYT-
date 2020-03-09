package com.andrew.newsapp.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.andrew.newsapp.entities.DbNewsPiece
import com.andrew.newsapp.fakes.FakeTopStoriesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.core.Is.`is`
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
/*

@ExperimentalCoroutinesApi
class GetTopStoriesUseCaseTest {
    @get:Rule
    val instantTaskExecutor = InstantTaskExecutorRule()

    @Test
    fun `invoke with ideal condition then successfully get top stories`() {
        //given list of top stories
        val repository = FakeTopStoriesRepository()
        val topStories = listOf(DbNewsPiece(), DbNewsPiece(), DbNewsPiece())
        repository.data = topStories

        //when invoked
        val returnedStories = getTopStories(repository)

        //then successfully get top stories
        assertThat(returnedStories.value, `is`(topStories))
    }
}*/

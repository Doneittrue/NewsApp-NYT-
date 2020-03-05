package com.andrew.newsapp.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.andrew.newsapp.entities.DbNewsPiece
import com.andrew.newsapp.fakes.FakeTopStoriesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.core.Is.`is`
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.internal.matchers.Equals

@ExperimentalCoroutinesApi
class GetTopStoriesUseCaseTest {
    @get:Rule
    val instantTaskExecutor = InstantTaskExecutorRule()

    private lateinit var repository: FakeTopStoriesRepository
    private lateinit var useCase: GetTopStoriesUseCase
    private lateinit var state: MutableLiveData<TopStoriesState>

    @Before
    fun initStatics() {
        repository = FakeTopStoriesRepository()
        useCase = GetTopStoriesUseCase(repository)
        state = MutableLiveData<TopStoriesState>().apply { value = Idle }
    }

    @After
    fun backToIdle() {
        state.value = Idle
    }

    @Test
    fun `invoke with ideal condition then successfully get top stories`() {
        //given list of top stories
        val topStories = listOf(DbNewsPiece(), DbNewsPiece(), DbNewsPiece())
        repository.data = topStories

        //when invoked
        val returnedStories = useCase(state)

        //then successfully get top stories
        assert(state.value is Success)
        assertThat(returnedStories.value, `is`(topStories))
    }

    @Test
    fun `invoke with no top stories show error message`() {
        //given error message
        val errorMessage="No Stores To Show Please check your Connection Then Try Again"

        //when invoked
        useCase(state)

        //then successfully get top stories
        assert(state.value is Error)
        assertThat((state.value as Error).message, `is`(errorMessage))
    }
}
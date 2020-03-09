package com.andrew.newsapp.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.andrew.newsapp.fakes.FakeTopStoriesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.*

@ExperimentalCoroutinesApi
class RefreshTopStoriesUseCaseTest {

    @get:Rule
    val instantTaskExecutor = InstantTaskExecutorRule()

    private lateinit var repository: FakeTopStoriesRepository
    private lateinit var useCase: RefreshTopStoriesUseCase
    private lateinit var state: MutableLiveData<TopStoriesState>

    @Before
    fun initStatics() {
        repository = FakeTopStoriesRepository()
        useCase = RefreshTopStoriesUseCase(repository)
        state = MutableLiveData<TopStoriesState>().apply { value = Idle }
    }

    @After
    fun backToIdle() {
        state.value = Idle
    }

    @Test
    fun `invoke with ideal conditions then successfully refresh`() = runBlocking {
        //given successful response code
        repository.responseCode=200

        //when invoked with ideal conditions
        useCase(true, "", state)

        //then successfully refresh
        assert(state.value is Success)
    }

    @Test
    fun `invoke when not connected show error message`() = runBlocking {
        //given not connected & not connected message
        val isConnected=false
        val notConnectedMessage = "Check your internet connection"

        //when invoked with no connection
        useCase(isConnected, "", state)

        //then not connected message will appear
        assert(state.value is Error)
        assertThat((state.value as Error).message, `is`(notConnectedMessage))
    }

    @Test
    fun `invoke with bad response code show error message`() = runBlocking {
        //given error message
        val errorMessage = "Error While Loading"

        //when invoked with ideal conditions
        useCase(true, "", state)

        //then successfully refresh
        assert(state.value is Error)
        assertThat((state.value as Error).message, `is`(errorMessage))
    }

    @Test
    fun `invoke when loading then do not proceed to request`() = runBlocking {
        //Loading state
        state.value=Loading

        //when invoked
        useCase(true, "", state)

        //then does not proceed
        assert(state.value is Loading)
    }

    @Test
    fun `invoke when state has no value then proceed to request`() = runBlocking {
        //state with no value
        state.value=null
        repository.responseCode=200

        //when invoked
        useCase(true, "", state)

        //then requests successfully
        assert(state.value is Success)
    }

}

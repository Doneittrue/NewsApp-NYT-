package com.andrew.newsapp.domain

import com.andrew.newsapp.entities.NewsResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://api.nytimes.com/svc/topstories/v2/"
private const val API_KEY = "KEY"

//Top story types
const val ARTS = "arts"
const val HOME_STORIES = "home"
const val SCIENCE = "science"
const val U_S = "us"
const val WORLD = "world"

val types = listOf(ARTS, HOME_STORIES, SCIENCE, U_S, WORLD)

private val retrofitInstance by lazy {
    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
}

val newsApi: NewsApi by lazy { retrofitInstance.create(NewsApi::class.java) }

interface NewsApi {
    @GET("{type}.json")
    fun getTopStoriesAsync(
        @Path("type") type: String,
        @Query("api-key") key: String = API_KEY
    ): Deferred<Response<NewsResponse>>
}
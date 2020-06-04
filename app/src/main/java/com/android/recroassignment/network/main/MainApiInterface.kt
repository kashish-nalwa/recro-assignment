package com.android.recroassignment.network.main

import com.android.recroassignment.model.newslist.News
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface MainApiInterface {
    @GET("top-headlines")
    fun getTopHeadlines(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String,
        @Query("pageSize") pageSize: Int
    ): Flowable<News>
}
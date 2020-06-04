package com.android.recroassignment.source

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.android.recroassignment.model.newslist.Article

@Dao
interface ArticlesDao {

    @Insert
    suspend fun insertArticles(articles: List<Article>)

    @Query("SELECT * FROM articles_table")
    fun getArticles(): LiveData<List<Article>>

    @Query("SELECT * FROM articles_table WHERE articleId =:articledId")
    fun getArticleDetails(articledId: Int): LiveData<Article>

    @Query("DELETE FROM articles_table")
    suspend fun deleteAllArticles()
}
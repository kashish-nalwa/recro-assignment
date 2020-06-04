package com.android.recroassignment.source.articleDetails

import androidx.lifecycle.LiveData
import com.android.recroassignment.model.newslist.Article
import com.android.recroassignment.source.ArticlesDao
import com.android.recroassignment.source.ArticlesDatabase
import javax.inject.Inject

class ArticleDetailsRepository @Inject constructor(database: ArticlesDatabase) {
    private val dao: ArticlesDao

    init {
        dao = database.getArticlesDao()
    }

    fun getArticleDetails(articleId: Int): LiveData<Article> {
        return dao.getArticleDetails(articleId)
    }
}
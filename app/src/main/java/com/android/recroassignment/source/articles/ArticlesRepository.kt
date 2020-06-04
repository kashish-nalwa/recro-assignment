package com.android.recroassignment.source.articles

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.android.recroassignment.Constants
import com.android.recroassignment.model.newslist.Article
import com.android.recroassignment.model.newslist.News
import com.android.recroassignment.network.articles.MainApiInterface
import com.android.recroassignment.source.ArticlesDao
import com.android.recroassignment.source.ArticlesDatabase
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ArticlesRepository @Inject constructor(
    private val mainApiInterface: MainApiInterface
    , database: ArticlesDatabase
) {
    private lateinit var source: LiveData<News>
    private val articlesDao: ArticlesDao = database.getArticlesDao()

    fun getNewsLiveData(): LiveData<News> {
        return source
    }


    fun fetchTopHeadlines() {
        source = LiveDataReactiveStreams.fromPublisher(
            mainApiInterface.getTopHeadlines(Constants.COUNTRY, Constants.API_KEY, 100)
                .onErrorReturn {
                    return@onErrorReturn News(
                        Constants.ERROR,
                        1,
                        arrayListOf(Article(-1))
                    )
                }
                .subscribeOn(Schedulers.io())
        )

    }

    suspend fun insertArticles(articles: List<Article>) {
        articlesDao.deleteAllArticles()
        articlesDao.insertArticles(articles)
    }

    fun getArticles(): LiveData<List<Article>> {
        return articlesDao.getArticles()
    }

    companion object {
        val TAG = "ArticlesRepository"
    }
}
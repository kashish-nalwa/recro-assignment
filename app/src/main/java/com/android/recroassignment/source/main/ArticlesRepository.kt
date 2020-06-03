package com.android.recroassignment.source.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.android.recroassignment.Constants
import com.android.recroassignment.model.newslist.Article
import com.android.recroassignment.model.newslist.News
import com.android.recroassignment.network.main.MainApiInterface
import com.android.recroassignment.source.ArticlesDao
import com.android.recroassignment.source.ArticlesDatabase
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
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
            mainApiInterface.getTopHeadlines(Constants.COUNTRY, Constants.API_KEY)
                .onErrorReturn {
                    return@onErrorReturn News(Constants.ERROR, 0, ArrayList<Article>())
                }
                .subscribeOn(Schedulers.io())
        )

        /*mainApiInterface.getTopHeadlines(Constants.COUNTRY, Constants.API_KEY)
            *//* .onErrorReturn {
                 return@onErrorReturn News("Error", 0, ArrayList<Article>())
             }*//*
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .toObservable()
            .subscribe(object : Observer<News> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable?) {
                }

                override fun onNext(value: News?) {
                    value?.let {
                        insertArticles(it.articles)
                    }

                }

                override fun onError(e: Throwable?) {
                    Log.e(TAG, "onError: " + e?.message)
                }

            })
*/

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
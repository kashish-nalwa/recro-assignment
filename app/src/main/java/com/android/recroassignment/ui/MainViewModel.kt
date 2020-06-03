package com.android.recroassignment.ui

import android.util.Log
import androidx.lifecycle.*
import com.android.recroassignment.Constants
import com.android.recroassignment.model.newslist.Article
import com.android.recroassignment.source.main.ArticlesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: ArticlesRepository) :
    ViewModel() {

    private val newsResourceLiveData: MediatorLiveData<NewsResource<List<Article>>> =
        MediatorLiveData()

    fun getNewsResourceLiveData(): LiveData<NewsResource<List<Article>>> {
        return newsResourceLiveData
    }

    init {
        Log.e(TAG, "onCreate: " + "initialized")
        /* 1. changing flow first it will check in db if data comes to zero,
              then it will show loading and fetch from api then update db
           2. if its reater than zero,
              then it will show updating contents and make api hit and,
              then new data will replace old one in db and refresh UI*/
        newsResourceLiveData.value = NewsResource.Loading()
        repository.fetchTopHeadlines()

        newsResourceLiveData.addSource(repository.getArticles(), Observer {
            if (it.size > 0) {
                newsResourceLiveData.value = NewsResource.Success(it)
                Log.e(TAG, "Articles from DB: " + it.size)
            }
            newsResourceLiveData.removeSource(repository.getArticles())
        })
        newsResourceLiveData.addSource(repository.getNewsLiveData(), Observer {
            Log.e(
                TAG,
                "News data from API: " + it.status + " size of articles: " + it.articles.size
            )

            if (it.status.equals(Constants.ERROR)) {

                if (!(newsResourceLiveData.value is NewsResource.Success)) {
                    newsResourceLiveData.value = NewsResource.Error(it.status)
                }

            } else {
                insertArticles(it.articles)
            }

            /* if (it.status.equals("Error") && !(newsResourceLiveData.value is NewsResource.Success)) {
                 newsResourceLiveData.value = NewsResource.Error(it.status)
             } else if (it.status.equals("Error") && (newsResourceLiveData.value is NewsResource.Success)) {
             } else {
                 insertArticles(it.articles)
             }*/
            newsResourceLiveData.removeSource(repository.getNewsLiveData())
        })

    }

    fun insertArticles(articles: List<Article>) {
        Log.e(TAG, "insertArticles: " + articles.size)
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertArticles(articles)
        }
    }


    companion object {
        val TAG = "MainViewModel"
    }
}
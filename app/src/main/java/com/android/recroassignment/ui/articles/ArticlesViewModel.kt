package com.android.recroassignment.ui.articles

import androidx.lifecycle.*
import com.android.recroassignment.Constants
import com.android.recroassignment.model.newslist.Article
import com.android.recroassignment.source.articles.ArticlesRepository
import com.android.recroassignment.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ArticlesViewModel @Inject constructor(private val repository: ArticlesRepository) :
    ViewModel() {

    private val newsResourceLiveData: MediatorLiveData<ArticlesResource<List<Article>>> =
        MediatorLiveData()

    fun getNewsResourceLiveData(): LiveData<ArticlesResource<List<Article>>> {
        return newsResourceLiveData
    }

    init {
        Log.e(TAG, "onCreate: " + "initialized")
        newsResourceLiveData.value =
            ArticlesResource.Loading(
                arrayListOf(Article(-1))
            )
        fetchTopHeadlines()
        newsResourceLiveData.addSource(repository.getArticles(), Observer {
            if (it.size > 0) {
                newsResourceLiveData.value =
                    ArticlesResource.Success(it)
                Log.e(TAG, "Articles from DB: " + it.size)
            }
            newsResourceLiveData.removeSource(repository.getArticles())
        })
    }

    fun fetchTopHeadlines() {
        repository.fetchTopHeadlines()
        newsResourceLiveData.addSource(repository.getNewsLiveData(), Observer {
            Log.e(
                TAG,
                "News data from API: " + it.status + " size of articles: " + it.articles.size
            )

            if (it.status.equals(Constants.ERROR)) {
                newsResourceLiveData.value =
                    ArticlesResource.Error(
                        it.status,
                        arrayListOf(Article(-1))
                    )
                /*if (!(newsResourceLiveData.value is ArticlesResource.Success)) {
                    newsResourceLiveData.value =
                        ArticlesResource.Error(
                            it.status,
                            arrayListOf(Article(-1))
                        )
                }*/

            } else {
                insertArticles(it.articles)
            }

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
package com.android.recroassignment.ui.articles

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.recroassignment.R
import com.android.recroassignment.model.newslist.Article
import com.android.recroassignment.ui.articleDetails.ArticleDetailsActivity
import com.android.recroassignment.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_articles.*
import javax.inject.Inject

class ArticlesActivity : DaggerAppCompatActivity(),
    ArticleSelectionListener {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    lateinit var adapter: ArticlesRecyclerAdapter

    private lateinit var viewModel: ArticlesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articles)
        initViewModel()
        initView()
    }

    private fun initView() {
        recyclerNews.layoutManager = LinearLayoutManager(this)
        adapter =
            ArticlesRecyclerAdapter(this)
        recyclerNews.adapter = adapter

        refreshLayout.setOnRefreshListener {
            viewModel.fetchTopHeadlines()
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, providerFactory).get(ArticlesViewModel::class.java)
        viewModel.getNewsResourceLiveData().observe(this, Observer {
            when (it) {
                is ArticlesResource.Success -> {
                    Log.e(TAG, "NewsResource.Success: " + "updating news" + it.data.size)
                    refreshLayout.isRefreshing = false
                    adapter.updateList(it)
                }
                is ArticlesResource.Loading -> {
                    Log.e(TAG, "NewsResource.Loading: ")
                    adapter.updateList(it)

                }
                is ArticlesResource.Error -> {
                    refreshLayout.isRefreshing = false
                    Log.e(TAG, "NewsResource.Error: ")
                    adapter.onError(it)
                }
            }
        })
    }

    companion object {
        val TAG = "MainActivity"
    }

    override fun onArticleSelected(article: Article) {
        Log.e(TAG, "onArticleSelected: " + article.title)

        Intent(this, ArticleDetailsActivity::class.java).apply {
            putExtra(ArticleDetailsActivity.EXTRA_ARTICLE_ID, article.articleId)
            putExtra(ArticleDetailsActivity.EXTRA_ARTICLE, article)
            startActivity(this)
        }

    }

}

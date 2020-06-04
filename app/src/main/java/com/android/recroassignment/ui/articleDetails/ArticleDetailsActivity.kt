package com.android.recroassignment.ui.articleDetails

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.recroassignment.R
import com.android.recroassignment.model.newslist.Article
import com.android.recroassignment.viewmodel.ViewModelProviderFactory
import com.bumptech.glide.Glide
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_article_details.*
import javax.inject.Inject

class ArticleDetailsActivity : DaggerAppCompatActivity() {

    private var article: Article? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_details)
        setupActionBar()
        getArticleId(savedInstanceState)
    }

    private fun setupActionBar() {
        supportActionBar?.title = getString(R.string.text_article_details)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(EXTRA_ARTICLE, article)
    }

    private fun getArticleId(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            article = savedInstanceState.getParcelable(EXTRA_ARTICLE)
        } else {
            article = intent.getParcelableExtra(EXTRA_ARTICLE)
        }
        setData(article!!)
    }

    private fun setData(article: Article) {
        tvTitle.text = article.title
        tvContent.text = article.description
        if (article.author.isNullOrEmpty()) {
            tvAuthor.text = getString(R.string.text_anonymous)
        } else {
            tvAuthor.text = article.author
        }

        tvContent.text = article.description

        Glide.with(ivArticle)
            .load(article.urlToImage)
            .into(ivArticle)
    }


    companion object {
        val TAG = "ArticleDetailsActivity"
        val EXTRA_ARTICLE_ID = "EXTRA_ARTICLE_ID"
        val EXTRA_ARTICLE = "EXTRA_ARTICLE"
    }
}

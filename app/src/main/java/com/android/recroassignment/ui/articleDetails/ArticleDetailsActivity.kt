package com.android.recroassignment.ui.articleDetails

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.android.recroassignment.R
import com.android.recroassignment.model.newslist.Article
import com.bumptech.glide.Glide
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_article_details.*

class ArticleDetailsActivity : DaggerAppCompatActivity() {

    private var article: Article? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_details)
        setupActionBar()
        getArticleId(savedInstanceState)
    }

    private fun setupActionBar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = getString(R.string.text_article_details)
        toolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_back)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

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

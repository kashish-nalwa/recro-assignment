package com.android.recroassignment.ui.articles

import com.android.recroassignment.model.newslist.Article

interface ArticleSelectionListener {
    fun onArticleSelected(article: Article)
}
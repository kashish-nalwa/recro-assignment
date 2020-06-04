package com.android.recroassignment.di

import com.android.recroassignment.di.articles.ArticlesModule
import com.android.recroassignment.di.articles.ArticlesScope
import com.android.recroassignment.di.articles.ArticlesViewModelsModule
import com.android.recroassignment.ui.articles.ArticlesActivity
import com.android.recroassignment.ui.articleDetails.ArticleDetailsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {
    @ArticlesScope
    @ContributesAndroidInjector(
        modules = [ArticlesModule::class, ArticlesViewModelsModule::class]
    )
    abstract fun contributesMainActivity(): ArticlesActivity

    @ContributesAndroidInjector
    abstract fun contributesArticleDetailsActivity(): ArticleDetailsActivity
}
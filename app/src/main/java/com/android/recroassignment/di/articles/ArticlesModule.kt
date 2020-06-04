package com.android.recroassignment.di.articles

import com.android.recroassignment.network.main.MainApiInterface
import com.android.recroassignment.source.ArticlesDatabase
import com.android.recroassignment.source.main.ArticlesRepository
import com.android.recroassignment.ui.articles.ArticleSelectionListener
import com.android.recroassignment.ui.articles.ArticlesRecyclerAdapter
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class ArticlesModule {
    companion object {
        @ArticlesScope
        @Provides
        @JvmStatic
        fun providesMainApi(retrofit: Retrofit): MainApiInterface {
            return retrofit.create(MainApiInterface::class.java)
        }

        @ArticlesScope
        @Provides
        @JvmStatic
        fun providesArticlesRepository(
            mainApiInterface: MainApiInterface,
            articlesDatabase: ArticlesDatabase
        ): ArticlesRepository {
            return ArticlesRepository(mainApiInterface, articlesDatabase)
        }

        @ArticlesScope
        @Provides
        @JvmStatic
        fun providesArticlesRecyclerAdapter(listener: ArticleSelectionListener): ArticlesRecyclerAdapter {
            return ArticlesRecyclerAdapter(
                listener
            )
        }


    }
}
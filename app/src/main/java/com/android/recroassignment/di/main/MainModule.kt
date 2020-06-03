package com.android.recroassignment.di.main

import com.android.recroassignment.network.main.MainApiInterface
import com.android.recroassignment.source.ArticlesDatabase
import com.android.recroassignment.source.main.ArticlesRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class MainModule {
    companion object {
        @Provides
        fun providesMainApi(retrofit: Retrofit): MainApiInterface {
            return retrofit.create(MainApiInterface::class.java)
        }

        @Provides
        fun providesArticlesRepository(
            mainApiInterface: MainApiInterface,
            articlesDatabase: ArticlesDatabase
        ): ArticlesRepository {
            return ArticlesRepository(mainApiInterface, articlesDatabase)
        }

    }
}
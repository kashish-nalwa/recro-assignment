package com.android.recroassignment.di

import com.android.recroassignment.BaseApplication
import com.android.recroassignment.Constants
import com.android.recroassignment.source.ArticlesDatabase
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {
    companion object {
        @Singleton
        @Provides
        fun providesHttpClient(): OkHttpClient {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
        }

        @Singleton
        @Provides
        fun providesRetrofitInstance(httpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .build()
        }

        @Singleton
        @Provides
        fun providesArticlesDatabase(application: BaseApplication): ArticlesDatabase {
            return ArticlesDatabase.getDatabase(application)
        }

    }
}